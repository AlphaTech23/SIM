#!/usr/bin/env bash
# Diagnostic STRICTEMENT EN LECTURE SEULE de l'etat du serveur.
# N'installe rien, ne modifie rien, ne demarre rien.
#
# Usage depuis ta machine (rien n'est copie sur le serveur) :
#   ssh root@161.97.109.65 'bash -s' < deploy/00-verifier.sh

ok()      { printf '  \033[32m[FAIT]\033[0m    %s\n' "$1"; }
ko()      { printf '  \033[31m[MANQUE]\033[0m  %s\n' "$1"; }
info()    { printf '  \033[33m[INFO]\033[0m    %s\n' "$1"; }
titre()   { printf '\n\033[1m== %s ==\033[0m\n' "$1"; }

P1=0; P2=0; P3=0; P4=0   # compteurs de manques par phase

titre "PHASE 1 - Paquets et systeme"
for p in java nginx psql ufw; do
  if command -v "$p" >/dev/null 2>&1; then
    case "$p" in
      java) ok "java present : $(java -version 2>&1 | head -1)" ;;
      psql) ok "postgresql present : $(psql --version)" ;;
      *)    ok "$p present" ;;
    esac
  else
    ko "$p absent"; P1=$((P1+1))
  fi
done

id sim >/dev/null 2>&1 && ok "utilisateur systeme 'sim' cree" || { ko "utilisateur 'sim' absent"; P1=$((P1+1)); }

for d in /opt/sim /opt/sim/config /var/log/sim /var/www/sim /etc/sim; do
  [ -d "$d" ] && ok "repertoire $d" || { ko "repertoire $d absent"; P1=$((P1+1)); }
done

if [ -f /etc/sim/sim.env ]; then
  ok "secret /etc/sim/sim.env present (permissions $(stat -c '%a %U:%G' /etc/sim/sim.env))"
  grep -q '^JWT_SECRET=' /etc/sim/sim.env     && ok "JWT_SECRET defini (sinon la cle versionnee du depot serait utilisee)"     || { ko "JWT_SECRET absent : jetons forgeables avec la cle publique du depot"; P1=$((P1+1)); }
else
  ko "/etc/sim/sim.env absent"; P1=$((P1+1))
fi

if command -v ufw >/dev/null 2>&1; then
  if ufw status 2>/dev/null | grep -q "Status: active"; then
    ok "pare-feu actif"
    info "regles : $(ufw status 2>/dev/null | awk '/ALLOW/{printf "%s ", $1}')"
  else
    info "pare-feu installe mais inactif"
  fi
fi

titre "PHASE 2 - Base de donnees"
if systemctl is-active --quiet postgresql 2>/dev/null; then
  ok "service postgresql demarre"
  if sudo -u postgres psql -tAc "SELECT 1 FROM pg_roles WHERE rolname='sim'" 2>/dev/null | grep -q 1; then
    ok "role 'sim' existe"
  else
    ko "role 'sim' absent"; P2=$((P2+1))
  fi
  if sudo -u postgres psql -tAc "SELECT 1 FROM pg_database WHERE datname='SIM'" 2>/dev/null | grep -q 1; then
    ok "base 'SIM' existe"
    NT=$(sudo -u postgres psql -d SIM -tAc "SELECT count(*) FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'" 2>/dev/null || echo 0)
    NV=$(sudo -u postgres psql -d SIM -tAc "SELECT count(*) FROM information_schema.views  WHERE table_schema='public'" 2>/dev/null || echo 0)
    NS=$(sudo -u postgres psql -d SIM -tAc "SELECT count(*) FROM statut" 2>/dev/null || echo 0)
    NU=$(sudo -u postgres psql -d SIM -tAc "SELECT count(*) FROM users" 2>/dev/null || echo 0)
    [ "$NT" -eq 17 ] && ok "17 tables presentes" || { ko "tables : $NT/17 (15 metier + users + roles)"; P2=$((P2+1)); }
    [ "$NV" -eq 5 ]  && ok "5 vues presentes"   || { ko "vues : $NV/5 (les endpoints /vrendezvous* echoueront)"; P2=$((P2+1)); }
    [ "$NS" -ge 4 ]  && ok "statuts de reference : $NS"  || { ko "statuts : $NS/4 (les vues filtrees seront vides)"; P2=$((P2+1)); }
    [ "$NU" -ge 1 ] \
      && ok "compte applicatif : $(sudo -u postgres psql -d SIM -tAc "SELECT name FROM users LIMIT 1" 2>/dev/null)" \
      || { ko "table users vide : aucune connexion possible, donc API entierement inaccessible"; P2=$((P2+1)); }
    NR=$(sudo -u postgres psql -d SIM -tAc "SELECT count(*) FROM rendez_vous" 2>/dev/null || echo "?")
    info "donnees metier : $NR rendez-vous"
  else
    ko "base 'SIM' absente"; P2=$((P2+1))
  fi
else
  ko "postgresql non demarre"; P2=$((P2+1))
fi

titre "PHASE 3 - Configuration"
[ -f /opt/sim/config/application-prod.yml ] && ok "application-prod.yml depose" || { ko "application-prod.yml absent"; P3=$((P3+1)); }
[ -f /etc/systemd/system/sim-backend.service ] && ok "unite systemd deposee" || { ko "unite systemd absente"; P3=$((P3+1)); }
[ -f /etc/nginx/sites-available/sim ] && ok "site nginx disponible" || { ko "site nginx absent"; P3=$((P3+1)); }
[ -L /etc/nginx/sites-enabled/sim ] && ok "site nginx active" || { ko "site nginx non active"; P3=$((P3+1)); }
[ -e /etc/nginx/sites-enabled/default ] && { ko "le site nginx 'default' est encore actif (il repondra a sa place)"; P3=$((P3+1)); } || ok "site 'default' desactive"
systemctl is-enabled --quiet sim-backend 2>/dev/null && ok "sim-backend active au demarrage" || { ko "sim-backend non active au demarrage"; P3=$((P3+1)); }
command -v nginx >/dev/null 2>&1 && { nginx -t >/dev/null 2>&1 && ok "configuration nginx valide" || ko "configuration nginx INVALIDE (nginx -t)"; }

titre "PHASE 4 - Application deployee"
if [ -f /opt/sim/sim.jar ]; then
  ok "JAR present ($(du -h /opt/sim/sim.jar | cut -f1), modifie le $(date -r /opt/sim/sim.jar '+%Y-%m-%d %H:%M'))"
else
  ko "/opt/sim/sim.jar absent"; P4=$((P4+1))
fi
if [ -f /var/www/sim/index.html ]; then
  ok "front deploye ($(find /var/www/sim -type f | wc -l) fichiers, modifie le $(date -r /var/www/sim/index.html '+%Y-%m-%d %H:%M'))"
else
  ko "front absent de /var/www/sim"; P4=$((P4+1))
fi
[ -f /opt/sim/config/column-visibility.json ] && ok "column-visibility.json present" || { ko "column-visibility.json absent"; P4=$((P4+1)); }

if systemctl is-active --quiet sim-backend 2>/dev/null; then
  ok "service sim-backend DEMARRE"
else
  ko "service sim-backend arrete"; P4=$((P4+1))
fi
systemctl is-active --quiet nginx 2>/dev/null && ok "nginx demarre" || { ko "nginx arrete"; P4=$((P4+1)); }

echo
if curl -sf -o /dev/null -m 5 http://127.0.0.1:8080/sim/statuts 2>/dev/null; then
  ok "API repond en local (127.0.0.1:8080/sim/statuts)"
else
  ko "API ne repond pas en local"; P4=$((P4+1))
fi
if curl -sf -o /dev/null -m 5 http://127.0.0.1/ 2>/dev/null; then
  ok "nginx sert le front en local"
else
  ko "nginx ne sert pas le front"; P4=$((P4+1))
fi

titre "RESUME"
for n in 1 2 3 4; do
  eval "m=\$P$n"
  case $n in
    1) lib="Phase 1 - provisionner le serveur   (01-provision-serveur.sh)" ;;
    2) lib="Phase 2 - charger le schema         (03-schema.sql)" ;;
    3) lib="Phase 3 - deposer les configs       (scp + liens)" ;;
    4) lib="Phase 4 - deployer l'application    (02-deployer.sh)" ;;
  esac
  if [ "$m" -eq 0 ]; then printf '  \033[32mOK\033[0m        %s\n' "$lib"
  else printf '  \033[31mA FAIRE\033[0m   %s  (%s point(s) manquant(s))\n' "$lib" "$m"; fi
done
echo
