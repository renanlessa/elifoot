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