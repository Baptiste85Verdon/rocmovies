-- Styles
INSERT INTO style (style_name) VALUES ('Action');
INSERT INTO style (style_name) VALUES ('Comédie');
INSERT INTO style (style_name) VALUES ('Drame');

-- Producteurs
INSERT INTO productor (name) VALUES ('Lucasfilm');
INSERT INTO productor (name) VALUES ('Marvel Studios');
INSERT INTO productor (name) VALUES ('Studio Ghibli');

-- Acteurs
INSERT INTO actor (firstname, lastname, birthdate) VALUES ('Leonardo', 'DiCaprio', '1974-11-11');
INSERT INTO actor (firstname, lastname, birthdate) VALUES ('Scarlett', 'Johansson', '1984-11-22');
INSERT INTO actor (firstname, lastname, birthdate) VALUES ('Tom', 'Hanks', '1956-07-09');
INSERT INTO actor (firstname, lastname, birthdate) VALUES ('Emma', 'Stone', '1988-11-06');

-- Films
INSERT INTO movie (title, reference, production_year, produced_by, style_id) VALUES ('Inception', 'IN-201010', 2010, 1, 1);
INSERT INTO movie (title, reference, production_year, produced_by, style_id) VALUES ('Avengers', 'AV-201212', 2012, 2, 1);
INSERT INTO movie (title, reference, production_year, produced_by, style_id) VALUES ('La La Land', 'LL-201616', 2016, 3, 2);

-- Clients
INSERT INTO customer (name) VALUES ('Jean Dupont');
INSERT INTO customer (name) VALUES ('Marie Martin');
INSERT INTO customer (name) VALUES ('Pierre Lefebvre');

-- Emprunts
INSERT INTO borrow (customer_id, movie_id, date, status) VALUES (1, 1, '2025-11-20', 'EN_COURS');
INSERT INTO borrow (customer_id, movie_id, date, status) VALUES (2, 2, '2025-11-18', 'TERMINE');
INSERT INTO borrow (customer_id, movie_id, date, status) VALUES (3, 3, '2025-11-22', 'EN_COURS');

-- Rôles
INSERT INTO role (actor_id, movie_id, role) VALUES (1, 1, 'Dom Cobb');
INSERT INTO role (actor_id, movie_id, role) VALUES (2, 2, 'Black Widow');
INSERT INTO role (actor_id, movie_id, role) VALUES (3, 2, 'Captain America');
INSERT INTO role (actor_id, movie_id, role) VALUES (4, 3, 'Mia');
