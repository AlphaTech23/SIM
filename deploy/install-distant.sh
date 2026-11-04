#!/usr/bin/env bash
# Execute SUR LE SERVEUR par 02-deployer.sh, apres que les artefacts
# ont ete deposes dans /tmp. Ne pas lancer a la main depuis ta machine.
set -e

echo "    deploiement du front"
rm -rf /var/www/sim/*
tar xzf /tmp/front.tgz -C /var/www/sim
chown -R www-data:www-data /var/www/sim

echo "    deploiement du backend"
mv /tmp/sim.jar /opt/sim/sim.jar
# ne pas ecraser la config si l API l a deja modifiee en ligne
if [ ! -f /opt/sim/config/column-visibility.json ]; then
  mv /tmp/column-visibility.json /opt/sim/config/
fi
rm -f /tmp/column-visibility.json /tmp/front.tgz /tmp/install-distant.sh
chown -R sim:sim /opt/sim
chmod 644 /opt/sim/sim.jar

echo "    redemarrage"
systemctl restart sim-backend

# /dev/tcp plutot que curl : curl n est pas garanti sur une Ubuntu minimale
for i in $(seq 1 30); do
  if (echo > /dev/tcp/127.0.0.1/8080) 2>/dev/null; then
    echo "    backend a l ecoute sur 8080"
    if command -v curl >/dev/null 2>&1; then
      echo "    /sim/statuts -> $(curl -s -o /dev/null -w %{http_code} http://127.0.0.1:8080/sim/statuts)"
    fi
    exit 0
  fi
  sleep 2
done

echo "    ECHEC : backend injoignable apres 60s"
systemctl is-active sim-backend || true
journalctl -u sim-backend -n 40 --no-pager || true
exit 1
