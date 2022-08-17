CREATE TABLE roles
(
    id_role serial,
    name_role character varying(30) NOT NULL,
    id_user serial,
    PRIMARY KEY (id_role)
);
INSERT INTO roles (name_role)
VALUES ('admin'),
       ('user'),
       ('unknown');

CREATE TABLE users
(
    id_user serial,
    first_name character varying(40) NOT NULL,
    last_name character varying(40) NOT NULL,
    login character varying(20) NOT NULL,
    password character varying(64) NOT NULL,
    PRIMARY KEY (id_user)
);
INSERT INTO users (first_name, last_name, login, password)
VALUES ( 'Kirill', 'Pyatyshev', 'admin', 'admin'),
       ( 'Bob', 'Pinger', 'user', 'user'),
       ( 'Noname', 'Noname', 'noname', 'noname');

CREATE TABLE users_roles
(
    id_users_roles serial,
    user_id serial REFERENCES users(id_user) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id serial REFERENCES roles(id_role) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (id_users_roles)
);
INSERT INTO users_roles (user_id, role_id)
VALUES ( 1, 1),
       ( 1, 2),
       ( 2, 2),
       ( 3, 3);