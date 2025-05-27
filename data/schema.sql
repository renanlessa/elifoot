CREATE SEQUENCE stadium_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE club_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE player_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE stadium (
     id BIGINT PRIMARY KEY DEFAULT nextval('stadium_seq'),
     name VARCHAR(255) NOT NULL,
     city VARCHAR(255) NOT NULL,
     capacity INTEGER,
     url_img VARCHAR(255)
);

CREATE TABLE club (
    id BIGINT PRIMARY KEY DEFAULT nextval('club_seq'),
    name VARCHAR(255) NOT NULL,
    founded DATE NOT NULL,
    url_img VARCHAR(255),
    created_at TIMESTAMP,
    active BOOLEAN,
    stadium_id BIGINT UNIQUE,
    CONSTRAINT fk_club_stadium FOREIGN KEY (stadium_id) REFERENCES stadium(id)
);

CREATE TABLE player (
    id BIGINT PRIMARY KEY DEFAULT nextval('player_seq'),
    name VARCHAR(255) NOT NULL,
    position VARCHAR(50) NOT NULL,
    shirt_number INTEGER NOT NULL,
    url_img VARCHAR(255),
    club_id BIGINT NOT NULL,
    CONSTRAINT fk_player_club FOREIGN KEY (club_id) REFERENCES club(id)
);

INSERT INTO stadium (id, name, city, capacity) VALUES
    (nextval('stadium_seq'), 'Estadio Centenario', 'Montevideo', 60000),
    (nextval('stadium_seq'), 'Gran Parque Central', 'Montevideo', 34000),
    (nextval('stadium_seq'), 'Estadio Campeón del Siglo', 'Montevideo', 40000),
    (nextval('stadium_seq'), 'Estadio Luis Franzini', 'Montevideo', 18000),
    (nextval('stadium_seq'), 'Estadio Belvedere', 'Montevideo', 10000),
    (nextval('stadium_seq'), 'Estadio Jardines del Hipódromo', 'Montevideo', 18000);

INSERT INTO club (id, name, founded, stadium_id, created_at, active) VALUES
    (nextval('club_seq'), 'Club Nacional de Football', '1899-05-14', 2, now(), true),
    (nextval('club_seq'), 'Club Atlético Peñarol', '1891-09-28', 3, now(), true),
    (nextval('club_seq'), 'Defensor Sporting Club', '1913-03-15', 4, now(), true),
    (nextval('club_seq'), 'Liverpool Fútbol Club', '1915-06-15', 5, now(), true),
    (nextval('club_seq'), 'Danubio Fútbol Club', '1932-03-01', 6, now(), true),
    (nextval('club_seq'), 'Uruguay National Team', '1901-05-30', 1, now(), true);

-- Jogadores do Nacional
INSERT INTO player (id, name, position, shirt_number, club_id) VALUES
    (nextval('player_seq'), 'Luis Suárez', 'FORWARD', 9, 1),
    (nextval('player_seq'), 'Rodrigo Erramuspe', 'DEFENDER', 2, 1);

-- Jogadores do Peñarol
INSERT INTO player (id, name, position, shirt_number, club_id) VALUES
    (nextval('player_seq'), 'Facundo Torres', 'MIDFIELDER', 10, 2),
    (nextval('player_seq'), 'Kevin Dawson', 'GOALKEEPER', 1, 2);

-- Jogadores do Defensor
INSERT INTO player (id, name, position, shirt_number, club_id) VALUES
    (nextval('player_seq'), 'Álvaro Navarro', 'FORWARD', 7, 3),
    (nextval('player_seq'), 'Emiliano Álvarez', 'DEFENDER', 4, 3);

-- Jogadores do Liverpool
INSERT INTO player (id, name, position, shirt_number, club_id) VALUES
    (nextval('player_seq'), 'Juan Ignacio Ramírez', 'FORWARD', 11, 4),
    (nextval('player_seq'), 'Camilo Cándido', 'MIDFIELDER', 6, 4);

-- Jogadores do Danubio
INSERT INTO player (id, name, position, shirt_number, club_id) VALUES
    (nextval('player_seq'), 'Santiago Mederos', 'MIDFIELDER', 8, 5),
    (nextval('player_seq'), 'Mauro Goicoechea', 'GOALKEEPER', 1, 5);

-- Jogadores da Seleção do Uruguai (Clube 6)
INSERT INTO player (id, name, position, shirt_number, club_id) VALUES
    (nextval('player_seq'), 'Federico Valverde', 'MIDFIELDER', 15, 6),
    (nextval('player_seq'), 'Ronald Araújo', 'DEFENDER', 4, 6);

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE scopes_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('users_seq'),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE scopes (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('scopes_seq'),
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_scopes (
    user_id BIGINT NOT NULL,
    scope_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, scope_id),
    CONSTRAINT fk_user_scopes_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_scopes_scope FOREIGN KEY (scope_id) REFERENCES scopes(id)
);

insert into scopes(id, name) values(nextval('scopes_seq'), 'admin:all');
insert into scopes(id, name) values(nextval('scopes_seq'), 'stadium:write');
insert into scopes(id, name) values(nextval('scopes_seq'), 'stadium:read');
insert into scopes(id, name) values(nextval('scopes_seq'), 'club:write');
insert into scopes(id, name) values(nextval('scopes_seq'), 'club:read');
insert into scopes(id, name) values(nextval('scopes_seq'), 'player:write');
insert into scopes(id, name) values(nextval('scopes_seq'), 'player:read');

insert into users(id, name, email, password, active) values(nextval('users_seq'), 'admin', 'admin@java10x', '$2a$10$VrIbJURwINOR5HOrWFFTNOwSILsioRJSuOGAg8Luvr9qZDSOl5JXG', true);
insert into users(id, name, email, password, active) values(nextval('users_seq'), 'user', 'user@java10x', '$2a$10$VrIbJURwINOR5HOrWFFTNOwSILsioRJSuOGAg8Luvr9qZDSOl5JXG', true);

insert into users_scopes(user_id, scope_id) values(1, 1);
insert into users_scopes(user_id, scope_id) values(2, 3);
insert into users_scopes(user_id, scope_id) values(2, 5);
insert into users_scopes(user_id, scope_id) values(2, 7);