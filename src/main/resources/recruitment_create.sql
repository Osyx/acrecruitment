-- Title: Recruitment DB
-- Last modification date: 2018-02-20 10:52:02.892

DROP DATABASE IF EXISTS `recruitment`;

CREATE SCHEMA IF NOT EXISTS `recruitment` DEFAULT CHARACTER SET utf8 ;
USE `recruitment` ;

-- tables
-- Table: Person
CREATE TABLE IF NOT EXISTS person(
    person_id bigint NOT NULL auto_increment,
    name varchar(40) NOT NULL,
    surname varchar(40) NOT NULL,
    ssn varchar(40) UNIQUE,
    email varchar(40),
    role_id bigint NOT NULL DEFAULT 2,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT person_pk PRIMARY KEY (person_id)
);

-- Table: availability
CREATE TABLE IF NOT EXISTS availability (
    availability_id bigint NOT NULL auto_increment,
    person_id bigint NOT NULL,
    from_date date NOT NULL,
    to_date date,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT availability_pk PRIMARY KEY (availability_id)
);

-- Table: experience
CREATE TABLE IF NOT EXISTS experience (
    experience_id bigint NOT NULL auto_increment,
    name varchar(40) NOT NULL UNIQUE,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT experience_pk PRIMARY KEY (experience_id)
);

-- Table: person_experience
CREATE TABLE IF NOT EXISTS person_experience (
    person_experience_id bigint NOT NULL auto_increment,
    person_id bigint NOT NULL,
    experience_id bigint NOT NULL,
    years_of_experience double(4,2) NOT NULL,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT person_experience_pk PRIMARY KEY (person_experience_id)
);

-- Table: role
CREATE TABLE IF NOT EXISTS role (
    role_id bigint NOT NULL auto_increment,
    name varchar(40) NOT NULL UNIQUE,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT role_pk PRIMARY KEY (role_id)
);

-- Table: user
CREATE TABLE IF NOT EXISTS user (
    user_id bigint NOT NULL auto_increment,
    person_id bigint NOT NULL,
    username varchar(40) NOT NULL UNIQUE,
    password varchar(40) NOT NULL,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);

-- Table: application
CREATE TABLE IF NOT EXISTS application (
    application_id bigint NOT NULL auto_increment,
    app_date date NOT NULL,
    person_id bigint NOT NULL UNIQUE,
    accepted bit,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT application_pk PRIMARY KEY (application_id)
);

-- foreign keys
-- Reference: availability_person (table: availability)
ALTER TABLE availability ADD CONSTRAINT availability_person FOREIGN KEY availability_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: person_experience_person (table: person_experience)
ALTER TABLE person_experience ADD CONSTRAINT person_experience_person FOREIGN KEY person_experience_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: person_experience_experience (table: person_experience)
ALTER TABLE person_experience ADD CONSTRAINT person_experience_experience FOREIGN KEY person_experience_experience (experience_id)
REFERENCES experience (experience_id) ON DELETE CASCADE;

-- Reference: user_person (table: user)
ALTER TABLE user ADD CONSTRAINT user_person FOREIGN KEY user_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: application_person (table: application)
ALTER TABLE application ADD CONSTRAINT application_person FOREIGN KEY application_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- INSERT DATA FROM OLD DATABASE
-- -----------------------------------------------
-- Insert the values for ROLE:
INSERT INTO role (role_id, name) VALUES (1, "recruit");
INSERT INTO role (role_id, name) VALUES (2, "applicant");

-- Insert the values for PERSON:
-- Greta Borg, original statement:
-- INSERT INTO person (person_id, name, surname, username, password, role_id) VALUES (1, 'Greta', 'Borg', 'borg', 'wl9nk23a', 1);
INSERT INTO person (person_id, name, surname, role_id) VALUES (1, 'Greta', 'Borg', 1);
INSERT INTO user (person_id, username, password) VALUES (1, 'borg', 'wl9nk23a');
-- Per Strand:
INSERT INTO person (person_id, name, surname, ssn, email, role_id) VALUES (2, 'Per', 'Strand', '19671212-1211', 'per@strand.kth.se', 2);

-- Insert the values for the AVAILABILITY:
INSERT INTO availability (availability_id, person_id, from_date, to_date) VALUES (1, 2, '2014-02-23', '2014-05-25');
INSERT INTO availability (availability_id, person_id, from_date, to_date) VALUES (2, 2, '2014-07-10', '2014-08-10');

-- Insert the values for competence, now called EXPERIENCE for more cohesion:
-- Original statement:
-- INSERT INTO competence (competence_id, name) VALUES (1, 'Korvgrillning');
INSERT INTO experience (experience_id, name) VALUES (1, 'Korvgrillning');
-- Original statement:
-- INSERT INTO competence (competence_id, name) VALUES (2, 'Karuselldrift');
INSERT INTO experience (experience_id, name) VALUES (2, 'Karuselldrift');

-- Insert the values for competence_profile, now called PERSON_EXPERIENCE for more obvious function and cohesion:
-- Original statement:
-- INSERT INTO competence_profile (competence_profile_id, person_id, competence_id, years_of_experience) VALUES (1, 2, 1, 3.5);
INSERT INTO person_experience (person_experience_id, person_id, experience_id, years_of_experience) VALUES (1, 2, 1, 3.5);
-- Original statement:
-- INSERT INTO competence_profile (competence_profile_id, person_id,competence_id, years_of_experience) VALUES (2, 2, 2, 2.0);
INSERT INTO person_experience (person_experience_id, person_id,experience_id, years_of_experience) VALUES (2, 2, 2, 2.0);

-- End of file.
