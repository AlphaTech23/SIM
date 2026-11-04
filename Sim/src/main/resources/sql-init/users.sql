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




