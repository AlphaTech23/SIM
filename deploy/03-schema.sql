-- ============================================================================
-- Schema complet de SIM, pour une base VIERGE.
--
-- Assemble automatiquement depuis les deux sources officielles du projet :
--   1. Base/0-table_a_jour_26-10-26.sql       (15 tables + 5 vues + statuts)
--   2. Sim/src/main/resources/sql-init/users.sql  (roles + users + compte SIM)
-- Ne pas editer a la main : regenerer depuis ces deux fichiers.
--
-- Charge par deploy/03-charger-schema.sh, qui verifie d'abord qu'aucune donnee
-- metier ne serait perdue.
-- ============================================================================

-- ===== 1/3  Schema metier (Base/0-table_a_jour_26-10-26.sql) =====
-- CREATE USER sim_user WITH PASSWORD 'sim2026!sim';

-- CREATE DATABASE sim_db;

-- \c "sim_db" sim_user

CREATE TABLE secteurs (
    id          SERIAL PRIMARY KEY,
    libele      VARCHAR(150) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE activites (
    id          SERIAL PRIMARY KEY,
    libele      VARCHAR(150) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE membres (
    id         SERIAL PRIMARY KEY,
    id_secteur INT NOT NULL REFERENCES secteurs(id) ON DELETE RESTRICT,
    intitule   VARCHAR(200) NOT NULL,
    logo       VARCHAR(255),
    telephone  VARCHAR(30),
    email      VARCHAR(150),
    whatsapp   VARCHAR(30),
    adresse    TEXT
);

CREATE TABLE activites_membres (
    id          SERIAL PRIMARY KEY,
    id_membre   INT NOT NULL REFERENCES membres(id) ON DELETE CASCADE,
    id_activite INT NOT NULL REFERENCES activites(id) ON DELETE CASCADE,
    UNIQUE (id_membre, id_activite)
);

CREATE TABLE sujets (
    id          SERIAL PRIMARY KEY,
    libele      VARCHAR(200) NOT NULL,
    description TEXT,
    nom       VARCHAR(100) NOT NULL,
    prenom    VARCHAR(100),
    fonction  VARCHAR(150),
    telephone VARCHAR(30),
    email     VARCHAR(150),
    whatsapp  VARCHAR(30),
    adresse   TEXT
);

CREATE TABLE sujets_membres (
    id        SERIAL PRIMARY KEY,
    id_membre INT NOT NULL REFERENCES membres(id) ON DELETE CASCADE,
    id_sujet  INT NOT NULL REFERENCES sujets(id) ON DELETE CASCADE,
    UNIQUE (id_membre, id_sujet)
);

CREATE TABLE statut (
    id     SERIAL PRIMARY KEY,
    libele VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE rendez_vous (
    id            SERIAL PRIMARY KEY,
    id_demandeur  INT NOT NULL REFERENCES membres(id) ON DELETE CASCADE,
    id_receveur   INT NOT NULL REFERENCES sujets_membres(id) ON DELETE CASCADE,
    contexte      TEXT,
    date_rdv      TIMESTAMP DEFAULT NULL  -- renseignée à la validation
);

CREATE TABLE statut_rendez_vous (
    id             SERIAL PRIMARY KEY,
    id_rendez_vous INT NOT NULL REFERENCES rendez_vous(id) ON DELETE CASCADE,
    id_statut      INT NOT NULL REFERENCES statut(id) ON DELETE RESTRICT,
    date           TIMESTAMP NOT NULL DEFAULT NOW(),
    remarque       TEXT
);

-- Recitification du rdv pour que le demandeur puisse proposer bcp de date
CREATE TABLE choix_horaire_demandeur(
    id  SERIAL PRIMARY KEY,
    id_rendez_vous INT NOT NULL REFERENCES rendez_vous(id) ON DELETE CASCADE,
    date_choix TIMESTAMP NOT NULL
);  

-- Offres
CREATE TABLE type_offres (
    id SERIAL PRIMARY KEY,
    libele VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE statut_offres (
    id SERIAL PRIMARY KEY,
    libele VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE offres (
    id SERIAL PRIMARY KEY,
    id_type_offre INT NOT NULL REFERENCES type_offres(id) ON DELETE RESTRICT,
    id_membre INT NOT NULL REFERENCES membres(id) ON DELETE RESTRICT,
    titre_offre VARCHAR(255) NOT NULL,
    descriptions TEXT,
    conditions TEXT,
    -- id_statut_offre INT NOT NULL REFERENCES statut_offres(id) ON DELETE RESTRICT,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_fin_validite DATE,

    CHECK (
        date_fin_validite IS NULL
        OR date_fin_validite >= date_creation::DATE
    )
);

CREATE TABLE cibles_offres (
    id SERIAL PRIMARY KEY,
    id_offre INT NOT NULL REFERENCES offres(id) ON DELETE CASCADE,
    id_membre INT NOT NULL REFERENCES membres(id) ON DELETE CASCADE,
    date_envoi TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (id_offre, id_membre)
);

CREATE TABLE histo_offres(
    id SERIAL PRIMARY KEY,
    id_offre INT NOT NULL REFERENCES offres(id) ON DELETE CASCADE,
    id_statut_offre INT NOT NULL REFERENCES statut_offres(id) ON DELETE RESTRICT,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remarque TEXT
);

CREATE INDEX idx_membres_secteur   ON membres(id_secteur);
CREATE INDEX idx_rdv_demandeur     ON rendez_vous(id_demandeur);
CREATE INDEX idx_rdv_receveur      ON rendez_vous(id_receveur);
CREATE INDEX idx_srv_rdv           ON statut_rendez_vous(id_rendez_vous);

INSERT INTO statut (libele)
VALUES
    ('en attente'),
    ('accepter'),
    ('refuser'),
    ('annuler')
ON CONFLICT (libele) DO NOTHING;



CREATE OR REPLACE VIEW v_rendez_vous AS
SELECT
    rdv.id AS id_rendez_vous,

    -- =====================================================
    -- INFORMATIONS DU DEMANDEUR (MEMBRE)
    -- =====================================================

    m_demandeur.id AS id_demandeur,
    m_demandeur.intitule AS membre_demandeur,
    m_demandeur.logo AS logo_demandeur,
    m_demandeur.telephone AS telephone_membre_demandeur,
    m_demandeur.email AS email_membre_demandeur,
    m_demandeur.whatsapp AS whatsapp_membre_demandeur,
    m_demandeur.adresse AS adresse_membre_demandeur,

    sec_demandeur.id AS id_secteur_demandeur,
    sec_demandeur.libele AS secteur_demandeur,
    sec_demandeur.description AS description_secteur_demandeur,

    -- =====================================================
    -- INFORMATIONS DU RECEVEUR (SUJET)
    -- =====================================================

    sm_receveur.id AS id_receveur,

    s_receveur.id AS id_sujet_receveur,
    s_receveur.libele AS libele_sujet_receveur,
    s_receveur.description AS description_sujet_receveur,
    s_receveur.nom AS nom_receveur,
    s_receveur.prenom AS prenom_receveur,
    s_receveur.fonction AS fonction_receveur,
    s_receveur.telephone AS telephone_receveur,
    s_receveur.email AS email_receveur,
    s_receveur.whatsapp AS whatsapp_receveur,
    s_receveur.adresse AS adresse_receveur,

    m_receveur.id AS id_membre_receveur,
    m_receveur.intitule AS membre_receveur,
    m_receveur.logo AS logo_receveur,
    m_receveur.telephone AS telephone_membre_receveur,
    m_receveur.email AS email_membre_receveur,
    m_receveur.whatsapp AS whatsapp_membre_receveur,
    m_receveur.adresse AS adresse_membre_receveur,

    sec_receveur.id AS id_secteur_receveur,
    sec_receveur.libele AS secteur_receveur,
    sec_receveur.description AS description_secteur_receveur,

    -- =====================================================
    -- INFORMATIONS DU RENDEZ-VOUS
    -- =====================================================

    rdv.contexte,
    rdv.date_rdv,

    -- =====================================================
    -- DERNIER STATUT
    -- =====================================================

    COALESCE(
        st.id,
        (
            SELECT s.id
            FROM statut s
            WHERE s.libele = 'en attente'
        )
    ) AS id_statut,

    COALESCE(st.libele, 'en attente') AS statut,

    sr.date AS date_changement_statut,
    sr.remarque,

    -- =====================================================
    -- HISTORIQUE COMPLET DES STATUTS
    -- =====================================================

    COALESCE(
        (
            SELECT jsonb_agg(
                jsonb_build_object(
                    'id', hist.id,
                    'id_statut', hist.id_statut,
                    'statut', stat_hist.libele,
                    'date', hist.date,
                    'remarque', hist.remarque
                )
                ORDER BY hist.date DESC, hist.id DESC
            )
            FROM statut_rendez_vous hist
            JOIN statut stat_hist
                ON stat_hist.id = hist.id_statut
            WHERE hist.id_rendez_vous = rdv.id
        ),
        '[]'::jsonb
    ) AS historique_statuts,

    -- =====================================================
    -- CRÉNEAUX HORAIRES
    -- =====================================================

        COALESCE(
        (
            SELECT jsonb_agg(
                jsonb_build_object(
                    'id', ch.id,
                    'date_choix', ch.date_choix
                )
                ORDER BY ch.date_choix
            )
            FROM choix_horaire_demandeur ch
            WHERE ch.id_rendez_vous = rdv.id
        ),
        '[]'::jsonb
    ) AS creneaux

FROM rendez_vous rdv

-- =====================================================
-- DEMANDEUR : MEMBRE
-- =====================================================

JOIN membres m_demandeur
    ON m_demandeur.id = rdv.id_demandeur

JOIN secteurs sec_demandeur
    ON sec_demandeur.id = m_demandeur.id_secteur

-- =====================================================
-- RECEVEUR : SUJET ASSOCIÉ À UN MEMBRE
-- =====================================================

JOIN sujets_membres sm_receveur
    ON sm_receveur.id = rdv.id_receveur

JOIN sujets s_receveur
    ON s_receveur.id = sm_receveur.id_sujet

JOIN membres m_receveur
    ON m_receveur.id = sm_receveur.id_membre

JOIN secteurs sec_receveur
    ON sec_receveur.id = m_receveur.id_secteur

-- =====================================================
-- DERNIER STATUT DU RENDEZ-VOUS
-- =====================================================

LEFT JOIN LATERAL (
    SELECT
        srv.id,
        srv.id_statut,
        srv.date,
        srv.remarque
    FROM statut_rendez_vous srv
    WHERE srv.id_rendez_vous = rdv.id
    ORDER BY srv.date DESC, srv.id DESC
    LIMIT 1
) sr ON TRUE

LEFT JOIN statut st
    ON st.id = sr.id_statut;

CREATE OR REPLACE VIEW v_rendez_vous_en_attente AS
SELECT *
FROM v_rendez_vous
WHERE statut = 'en attente';

CREATE OR REPLACE VIEW v_rendez_vous_acceptes AS
SELECT *
FROM v_rendez_vous
WHERE statut = 'accepter';

CREATE OR REPLACE VIEW v_rendez_vous_refuses AS
SELECT *
FROM v_rendez_vous
WHERE statut = 'refuser';

CREATE OR REPLACE VIEW v_rendez_vous_annules AS
SELECT *
FROM v_rendez_vous
WHERE statut = 'annuler';

-- ===== 2/3  Authentification (sql-init/users.sql) =====
--Default login
--Username: SIM
--Password: S26IM
--
--Le mot de passe est stocke hache en bcrypt (BCryptPasswordEncoder, force 10),
--jamais en clair. Pour le changer, regenerer un hash avec le meme encodeur :
--  new BCryptPasswordEncoder().encode("nouveauMotDePasse")
--puis remplacer la valeur ci-dessous.

CREATE TABLE IF NOT EXISTS roles
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
  password VARCHAR(100) NOT NULL,
  role_id INTEGER NOT NULL REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (name, email, password, role_id)
VALUES ('SIM', 'sim@user.com', '$2a$10$1fJ1.n4GAf9XMW5tl.ZCnOKBcYhr3Pdtc9J2eO9ondqytQPnws/rS', 1)
ON CONFLICT (email) DO NOTHING;





-- ===== 3/3  Droits du role applicatif =====
GRANT USAGE ON SCHEMA public TO sim;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO sim;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO sim;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO sim;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO sim;
