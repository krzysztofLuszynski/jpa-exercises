INSERT INTO hero(id, name, surname, birth_date) VALUES (1000, 'Jack', 'White', {ts '2010-01-01'});
INSERT INTO hero(id, name, surname, birth_date) VALUES (1001, 'Johny', 'Bravo', {ts '2000-02-02'});
INSERT INTO hero(id, name, surname, birth_date) VALUES (1002, 'Barbara', 'Straisand', {ts '1980-11-26'});
INSERT INTO hero(id, name, surname, birth_date) VALUES (1003, 'Tom', 'Goody', {ts '1992-03-13'});

INSERT INTO item(id, hero_id, name) VALUES (2000, 1000, 'gun');
INSERT INTO item(id, hero_id, name) VALUES (2001, 1000, 'helm');
INSERT INTO item(id, hero_id, name) VALUES (2002, 1000, 'boots');
INSERT INTO item(id, hero_id, name) VALUES (2003, 1000, 'armor');
INSERT INTO item(id, hero_id, name) VALUES (2004, 1000, 'gold');

INSERT INTO item(id, hero_id, name) VALUES (2005, 1001, 'sword');
INSERT INTO item(id, hero_id, name) VALUES (2006, 1001, 'shield');
INSERT INTO item(id, hero_id, name) VALUES (2007, 1001, 'boots');

INSERT INTO item(id, hero_id, name) VALUES (2008, 1003, 'microphone');
INSERT INTO item(id, hero_id, name) VALUES (2009, 1003, 'shoes');