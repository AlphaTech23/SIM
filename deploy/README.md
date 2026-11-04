# Déploiement de SIM sur 161.97.109.65

**Connexion à l'application : `SIM` / `S26IM`** (compte unique, rôle `ROLE_ADMIN`).
Le mot de passe est stocké haché en bcrypt dans `03-schema.sql` ; pour le changer,
voir l'en-tête de `Sim/src/main/resources/sql-init/users.sql`.

La création de comptes n'existe plus dans l'interface : elle se fait via
`POST /auth/register` dans Swagger, une fois authentifié.

Architecture visée — une seule origine, rien d'autre que nginx exposé :

```
Internet ──▶ nginx :80
               ├── /      ──▶ /var/www/sim          (build Vue statique)
               └── /sim/  ──▶ 127.0.0.1:8080        (JAR Spring Boot, systemd)
                                    │
                              127.0.0.1:5432         (PostgreSQL, non exposé)
```

Le front appelle `/sim` en relatif : même origine, donc **aucun CORS** en production.

## D'abord : où en est le serveur ?

`00-verifier.sh` est **en lecture seule** — il n'installe, ne modifie et ne démarre rien.
Il dit, phase par phase, ce qui est déjà fait et ce qui manque. À lancer avant de
commencer, entre deux phases, et après le déploiement.

```bash
ssh root@161.97.109.65 'bash -s' < deploy/00-verifier.sh
```

Rien n'est copié sur le serveur : le script est envoyé sur l'entrée standard de bash.

Test rapide depuis ta machine, sans même se connecter :

```bash
curl -s -o /dev/null -m 6 -w 'front: %{http_code}\n'  http://161.97.109.65/
curl -s -o /dev/null -m 6 -w 'api:   %{http_code}\n'  http://161.97.109.65/sim/statuts
```

Deux `200` = tout est déployé. Pas de réponse = rien n'écoute encore sur le port 80.

## Première installation

```bash
# 1. Provisionner le serveur (une seule fois, en root)
scp deploy/01-provision-serveur.sh root@161.97.109.65:/tmp/
ssh root@161.97.109.65 'bash /tmp/01-provision-serveur.sh'

# 2. Charger le schéma : 17 tables (15 métier + users + roles), 5 vues,
#    4 statuts et le compte applicatif SIM / S26IM.
#    Le script REFUSE d'écraser une base contenant des données métier ;
#    ajouter --force (après sauvegarde) pour passer outre.
scp deploy/03-schema.sql deploy/03-charger-schema.sh root@161.97.109.65:/tmp/
ssh root@161.97.109.65 'bash /tmp/03-charger-schema.sh'

# 3. Déposer les fichiers de configuration
scp deploy/application-prod.yml root@161.97.109.65:/opt/sim/config/
scp deploy/sim-backend.service  root@161.97.109.65:/etc/systemd/system/
scp deploy/nginx-sim.conf       root@161.97.109.65:/etc/nginx/sites-available/sim

ssh root@161.97.109.65 '
  ln -sf /etc/nginx/sites-available/sim /etc/nginx/sites-enabled/sim
  rm -f /etc/nginx/sites-enabled/default
  nginx -t && systemctl reload nginx
  systemctl daemon-reload && systemctl enable sim-backend
'
```

## Déploiements suivants

```bash
./deploy/02-deployer.sh          # build + envoi + redémarrage + vérification
```

## Vérifier / diagnostiquer

```bash
ssh root@161.97.109.65 'systemctl status sim-backend'
ssh root@161.97.109.65 'journalctl -u sim-backend -n 100 --no-pager'
ssh root@161.97.109.65 'tail -f /var/log/nginx/sim.error.log'
```

## Points à connaître

- **`npm run build` échoue** (256 erreurs TypeScript préexistantes dans le code généré).
  Le script de déploiement utilise `npm run build-only`, qui produit un `dist/` fonctionnel
  car Vite ne vérifie pas les types.
- **Les 5 vues `v_rendez_vous*` ne sont dans aucun SQL du dépôt.** `03-schema.sql` est un
  `pg_dump` de la base locale : c'est aujourd'hui la seule source du schéma. À versionner.
- **`column-visibility.json` est écrit à l'exécution** par l'API. Il doit rester inscriptible
  par l'utilisateur `sim` (`/opt/sim/config/`), et il est écrasé à chaque déploiement.
- **Pas de HTTPS possible en l'état** : Let's Encrypt ne délivre pas de certificat pour une
  IP nue. Il faut un nom de domaine pointant sur 161.97.109.65, puis `certbot --nginx`.
- **`application.yml` versionné contient `logging.level.root: TRACE`** et les identifiants
  `sim/sim` en dur. Le profil `prod` neutralise les deux, mais le fichier reste à nettoyer.
- **Scripts à lancer depuis Git Bash** sous Windows (pas PowerShell) : ils utilisent
  `ssh`/`scp`/`tar`. `rsync` n'est volontairement pas utilisé, il est absent de Windows.
- **Trois pièges Windows**, qui ont chacun fait échouer un déploiement avant d'être corrigés
  dans les scripts. À ne pas réintroduire :
  1. **Ne jamais alimenter l'entrée standard de `ssh`** (`tar | ssh`, `ssh <<EOF`) tant que
     l'authentification est par mot de passe : `ssh` ne peut plus le lire et renvoie
     `Connection closed`. Le script distant est donc envoyé par `scp` puis passé en argument.
  2. **Git Bash a son propre `ssh` (MSYS)** qui renvoie `Permission denied` avec le bon mot de
     passe quand il tourne dans une console Windows. Les scripts forcent
     `C:\WINDOWS\System32\OpenSSH\`.
  3. **Git Bash convertit toute valeur commençant par `/`** en chemin Windows :
     `VITE_APP_API_URL=/sim` devenait `C:/Program Files/Git/sim` dans le bundle. D'où
     `SimWebapp/.env.production`, qui échappe au shell. Ne pas remettre cette variable dans
     le script : une variable d'environnement a priorité sur le fichier.

  Ce troisième cas est le plus dangereux : le déploiement se déclare **réussi**, les codes HTTP
  sont tous à 200, et seule l'inspection du bundle révèle le problème :
  `curl http://161.97.109.65/assets/index-*.js | grep -o 'baseURL:"[^"]*"'` doit donner `/sim`.
- **L'API est exposée sans authentification**, décision assumée : aucun filtrage IP ni
  Basic Auth dans `nginx-sim.conf`. Les 10 endpoints `DELETE` sont donc publics. Pour
  restreindre plus tard, décommenter le bloc `allow/deny` du fichier nginx et l'étendre
  à `location /sim/`.


eteindre
ssh root@161.97.109.65 "systemctl stop sim-backend"

alumer 
ssh root@161.97.109.65 "systemctl start sim-backend"

restart
ssh root@161.97.109.65 "systemctl restart sim-backend"

mot de passe 
UGgQpmG3669NY9CAwoS8pD9