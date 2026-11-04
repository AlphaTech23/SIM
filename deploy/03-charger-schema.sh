#!/usr/bin/env bash
# A executer SUR LE SERVEUR, en root. Remplace le schema de la base SIM par
# celui de 03-schema.sql (15 tables metier + 5 vues + roles/users).
#
# DESTRUCTIF : supprime le schema public et tout ce qu'il contient.
# Par securite, le script REFUSE de s'executer s'il trouve des donnees metier,
# sauf si on passe --force explicitement.
#
#   bash 03-charger-schema.sh            # verifie puis charge si la base est vide
#   bash 03-charger-schema.sh --force    # charge meme s'il y a des donnees
set -euo pipefail

FORCE="${1:-}"
SQL="$(dirname "$0")/03-schema.sql"
[ -f "$SQL" ] || { echo "ERREUR : 03-schema.sql introuvable a cote du script"; exit 1; }

q() { sudo -u postgres psql -d SIM -tAc "$1" 2>/dev/null || echo 0; }

echo ">>> Etat actuel de la base SIM"
if ! sudo -u postgres psql -tAc "SELECT 1 FROM pg_database WHERE datname='SIM'" | grep -q 1; then
  echo "    la base SIM n'existe pas -- lance d'abord 01-provision-serveur.sh"
  exit 1
fi

NT=$(q "SELECT count(*) FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'")
NV=$(q "SELECT count(*) FROM information_schema.views WHERE table_schema='public'")
echo "    tables : $NT    vues : $NV"

# Compte les lignes des tables metier qui existent, pour ne rien ecraser par surprise
TOTAL=0
for t in secteurs activites membres sujets sujets_membres activites_membres \
         rendez_vous statut_rendez_vous plage_horaire choix_horaire_demandeur \
         offres type_offres statut_offres cibles_offres histo_offres users; do
  if [ "$(q "SELECT count(*) FROM information_schema.tables WHERE table_schema='public' AND table_name='$t'")" = "1" ]; then
    N=$(q "SELECT count(*) FROM $t")
    [ "$N" != "0" ] && { echo "    $t : $N ligne(s)"; TOTAL=$((TOTAL+N)); }
  fi
done
echo "    total de lignes metier : $TOTAL"

if [ "$TOTAL" -gt 0 ] && [ "$FORCE" != "--force" ]; then
  echo
  echo "ARRET : la base contient $TOTAL ligne(s). Elles seraient DEFINITIVEMENT perdues."
  echo "Fais une sauvegarde, puis relance avec --force si tu veux vraiment ecraser :"
  echo "    sudo -u postgres pg_dump SIM > /root/sim-sauvegarde-\$(date +%F-%H%M).sql"
  echo "    bash $0 --force"
  exit 1
fi

echo ">>> Remplacement du schema"
sudo -u postgres psql -d SIM -v ON_ERROR_STOP=1 -q \
  -c 'DROP SCHEMA public CASCADE;' \
  -c 'CREATE SCHEMA public;' \
  -c 'GRANT ALL ON SCHEMA public TO postgres;' \
  -c 'GRANT USAGE ON SCHEMA public TO sim;'

sudo -u postgres psql -d SIM -v ON_ERROR_STOP=1 -q -f "$SQL"

echo ">>> Verification"
echo "    tables : $(q "SELECT count(*) FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'") (attendu 17)"
echo "    vues   : $(q "SELECT count(*) FROM information_schema.views WHERE table_schema='public'") (attendu 5)"
echo "    statuts: $(q "SELECT string_agg(libele,', ' ORDER BY id) FROM statut")"
echo "    compte : $(q "SELECT u.name FROM users u")"
echo ">>> Termine."
