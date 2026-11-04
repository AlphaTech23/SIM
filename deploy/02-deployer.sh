#!/usr/bin/env bash
# A lancer depuis Git Bash, a la racine du depot : ./deploy/02-deployer.sh
# Construit le JAR + le front, les envoie, redemarre le service.
#
# Conçu pour l'authentification par MOT DE PASSE :
#  - jamais de pipe vers ssh (son entree standard servirait de mot de passe) ;
#  - tout est regroupe en 1 scp + 1 ssh, soit 2 saisies au lieu de 6.
set -euo pipefail

SERVEUR="${SERVEUR:-root@161.97.109.65}"
HOTE="${SERVEUR#*@}"
RACINE="$(cd "$(dirname "$0")/.." && pwd)"

# Git Bash fournit un ssh MSYS incapable de lire un mot de passe quand le script
# est lance depuis une console Windows (PowerShell) : il renvoie "Permission
# denied" meme avec le bon mot de passe. L OpenSSH natif de Windows y arrive, et
# il partage le known_hosts deja alimente par tes commandes manuelles.
WINSSH="/c/WINDOWS/System32/OpenSSH"
if [ -x "$WINSSH/ssh.exe" ] && [ -x "$WINSSH/scp.exe" ]; then
  SSH="$WINSSH/ssh.exe"; SCP="$WINSSH/scp.exe"
  echo "    (OpenSSH Windows)"
else
  SSH="ssh"; SCP="scp"
  echo "    (OpenSSH MSYS -- si Permission denied, lance depuis une fenetre Git Bash)"
fi

echo ">>> 1/4 Build backend"
(cd "$RACINE/Sim" && mvn -q clean package -DskipTests)
JAR="$(ls "$RACINE"/Sim/target/sim-*.jar | head -1)"
echo "    $(basename "$JAR") ($(du -h "$JAR" | cut -f1))"

echo ">>> 2/4 Build frontend"
# build-only et non 'npm run build' : le type-check echoue (256 erreurs preexistantes).
#
# VITE_APP_API_URL vient de SimWebapp/.env.production et PAS d'une variable de
# shell : Git Bash convertit "/sim" en "C:/Program Files/Git/sim" (conversion de
# chemins MSYS), ce qui compilait une baseURL invalide dans le bundle. Une
# variable d environnement aurait priorite sur le fichier, donc ne pas la remettre.
(cd "$RACINE/SimWebapp" && npm run build-only >/dev/null)
echo "    dist/ pret ($(du -sh "$RACINE/SimWebapp/dist" | cut -f1))"

echo ">>> 3/4 Envoi  --  mot de passe demande 1 fois"
STAGE="$(mktemp -d)"
trap 'rm -rf "$STAGE"' EXIT
tar czf "$STAGE/front.tgz" -C "$RACINE/SimWebapp/dist" .
cp "$JAR" "$STAGE/sim.jar"
cp "$RACINE/Sim/config/column-visibility.json" "$STAGE/column-visibility.json"
cp "$RACINE/deploy/install-distant.sh" "$STAGE/install-distant.sh"
( cd "$STAGE" && "$SCP" front.tgz sim.jar column-visibility.json install-distant.sh "$SERVEUR:/tmp/" )

echo ">>> 4/4 Installation et redemarrage  --  mot de passe demande 1 fois"
# Le script distant est passe en ARGUMENT, jamais sur l entree standard :
# celle-ci doit rester libre pour que ssh puisse lire le mot de passe.
"$SSH" "$SERVEUR" "bash /tmp/install-distant.sh"

echo ">>> Verification publique"
curl -s -m 10 -o /dev/null -w "    http://$HOTE/            -> %{http_code}\n" "http://$HOTE/"            || echo "    front injoignable"
curl -s -m 10 -o /dev/null -w "    http://$HOTE/sim/statuts -> %{http_code}\n" "http://$HOTE/sim/statuts" || echo "    api injoignable"
echo ">>> Termine."
