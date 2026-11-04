#!/usr/bin/env bash
# A executer UNE SEULE FOIS sur le serveur, en root.
set -euo pipefail

echo ">>> Paquets"
apt-get update
apt-get install -y openjdk-21-jre-headless nginx postgresql postgresql-contrib ufw

echo ">>> Utilisateur applicatif (sans shell)"
id -u sim &>/dev/null || adduser --system --group --home /opt/sim --no-create-home sim

echo ">>> Arborescence"
mkdir -p /opt/sim/config /var/log/sim /var/www/sim /etc/sim
chown -R sim:sim /opt/sim /var/log/sim
chown -R www-data:www-data /var/www/sim

echo ">>> Secret base de donnees"
if [ ! -f /etc/sim/sim.env ]; then
  DB_PWD="$(openssl rand -base64 24)"
  # application.yml embarque une valeur de repli pour jwt.secret, versionnee dans
  # le depot : sans JWT_SECRET ici, quiconque a acces au code peut forger un jeton
  # admin valide. On en genere donc un propre a cette machine.
  JWT_PWD="$(openssl rand -base64 48)"
  cat > /etc/sim/sim.env <<EOF
SIM_DB_USER=sim
SIM_DB_PASSWORD=${DB_PWD}
JWT_SECRET=${JWT_PWD}
EOF
  chown root:sim /etc/sim/sim.env
  chmod 640 /etc/sim/sim.env
  echo "    mot de passe genere dans /etc/sim/sim.env"
else
  echo "    /etc/sim/sim.env existe deja, mot de passe conserve"
  # Complete un fichier cree par une version precedente du script, qui ne
  # generait pas encore JWT_SECRET. On n'ecrase jamais une valeur existante.
  if ! grep -q '^JWT_SECRET=' /etc/sim/sim.env; then
    echo "JWT_SECRET=$(openssl rand -base64 48)" >> /etc/sim/sim.env
    echo "    JWT_SECRET manquant : genere et ajoute"
  else
    echo "    JWT_SECRET deja present"
  fi
fi
DB_PWD="$(grep SIM_DB_PASSWORD /etc/sim/sim.env | cut -d= -f2-)"

echo ">>> Role et base PostgreSQL"
sudo -u postgres psql -v ON_ERROR_STOP=1 <<EOF
DO \$\$ BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname='sim') THEN
    CREATE ROLE sim LOGIN PASSWORD '${DB_PWD}';
  ELSE
    ALTER ROLE sim PASSWORD '${DB_PWD}';
  END IF;
END \$\$;
EOF
sudo -u postgres psql -tAc "SELECT 1 FROM pg_database WHERE datname='SIM'" | grep -q 1 \
  || sudo -u postgres createdb -O sim SIM
echo "    base SIM prete"

echo ">>> Pare-feu (22/80/443 uniquement ; 5432 et 8080 restent fermes)"
ufw allow OpenSSH
ufw allow 80/tcp
ufw allow 443/tcp
ufw --force enable

systemctl enable --now postgresql nginx
echo ">>> Provisioning termine."
