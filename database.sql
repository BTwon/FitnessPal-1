CREATE DATABASE MyFitnessPal;

USE MyFitnessPal;

DROP TABLE workout;

SELECT * FROM routine;
SELECT * FROM muscle_group;
SELECT * FROM equipment;
SELECT * FROM muscle;
SELECT * FROM workout;

CREATE TABLE routine(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255),
	workout_ids VARCHAR(255),
    description VARCHAR(5000),
    intensity SET('Low Intensity', 'Medium Intensity', 'High Intensity'),
    image_content LONGBLOB,
    PRIMARY KEY(id)
);

CREATE TABLE equipment(
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(5000),
    workout_type ENUM('Weight Training', 'Cardio') NOT NULL DEFAULT 'Weight Training',
    image_content LONGBLOB,
    PRIMARY KEY(id),
    CONSTRAINT AK_UnqueName UNIQUE(name)
);

CREATE TABLE muscle(
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(5000),
    muscle_group_id INT NOT NULL,
    image_content LONGBLOB,
    PRIMARY KEY(id),
    CONSTRAINT AK_UnqueName UNIQUE(name)
);

CREATE TABLE muscle_group(
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(5000),
    image_content LONGBLOB,
    PRIMARY KEY(id),
    CONSTRAINT AK_UnqueName UNIQUE(name)
);


CREATE TABLE workout (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255),
	skill_level ENUM ('Beginner', 'Intermediate', 'Advanced') NOT NULL DEFAULT 'Beginner',
	type ENUM ('Weight Training', 'Cardio') NOT NULL DEFAULT 'Weight Training',
	equipment VARCHAR(255),
	muscles VARCHAR(255), 
    image_content LONGBLOB,
    video VARCHAR(255),
    instructions VARCHAR(5000),
	PRIMARY KEY(id),
    CONSTRAINT AK_UnqueName UNIQUE(name)
);

create trigger embedthevideo
before insert on workout
for each row
SET NEW.video= REPLACE(NEW.video, "https://www.youtube.com/watch?v=", "https://www.youtube.com/embed/");




INSERT INTO workout(name, skill_level, type, equipment, body_parts, instructions, video) VALUES ('Asdfasdf', 'Beginner', 'Weight Training', 'Dumbells', 'Tensor fasciae latae', 'Asdfsad', 'Asdfasdf');

SELECT id FROM workout WHERE (name = 'Asdf' AND skill_level = 'Beginner' AND type = 'Weight Training' AND equipment = 'Olympic Barbell' AND body_parts = 'Calves' AND instructions = 'Asdf' AND video = 'asdf');
SELECT * FROM workout WHERE body_parts LIKE 'Tensor fasciae latae' OR body_parts LIKE 'Pectoralis major';
DELETE FROM workout WHERE id = 1;

SELECT * FROM workout WHERE name LIKE 'Chest' OR equipment LIKE 'Chest' OR body_parts LIKE 'Chest' OR skill_level LIKE 'Chest' OR type LIKE 'Chest' OR instructions LIKE 'Chest' OR muscle_group LIKE 'Chest' ;

INSERT INTO muscle_group(name) VALUES ('Abs');
INSERT INTO muscle_group(name) VALUES ('Shoulders');
INSERT INTO muscle_group(name) VALUES ('Back');
INSERT INTO muscle_group(name) VALUES ('Legs');
INSERT INTO muscle_group(name) VALUES ('Buttocks');
INSERT INTO muscle_group(name) VALUES ('Hips');
INSERT INTO muscle_group(name) VALUES ('Chest');
INSERT INTO muscle_group(name) VALUES ('Heart');

SELECT * FROM workout;

SELECT image_content FROM muscle-group WHERE id = 8;

SELECT *, (SELECT name FROM muscle_group WHERE m.muscle_group_id = id ) AS MuscleGroup FROM muscle m;

SELECT (SELECT name FROM muscle_group WHERE m.muscle_group_id = id ) AS MuscleGroup FROM muscle m ;

SELECT *, (SELECT name FROM muscle_group 
WHERE e.muscle_groups = id ) AS muscle_group FROM equipment e;

SELECT name FROM muscle WHERE muscle_group_id = 1;

SELECT * FROM workout WHERE name LIKE '%Curl%';

OR equipment LIKE 'curl' OR muscles LIKE 'curl' OR skill_level LIKE 'curl' OR type LIKE 'curl' OR instructions LIKE 'curl';

SELECT name FROM muscle;
SELECT name 
FROM muscle_group 
WHERE id=1;

