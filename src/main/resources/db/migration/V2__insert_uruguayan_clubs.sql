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