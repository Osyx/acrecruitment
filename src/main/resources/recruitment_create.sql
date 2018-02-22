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
    ssn varchar(40) NOT NULL UNIQUE,
    email varchar(40) NOT NULL UNIQUE,
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

-- Table: person_role
CREATE TABLE IF NOT EXISTS person_role (
    person_id bigint NOT NULL,
    role_id bigint NOT NULL,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT person_role_pk PRIMARY KEY (person_id,role_id)
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
    person_id bigint NOT NULL,
    accepted bit,
    version int NOT NULL DEFAULT 1,
    CONSTRAINT application_pk PRIMARY KEY (application_id)
);

-- foreign keys
-- Reference: availability_Person (table: availability)
ALTER TABLE availability ADD CONSTRAINT availability_person FOREIGN KEY availability_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: person_experience_Person (table: person_experience)
ALTER TABLE person_experience ADD CONSTRAINT person_experience_person FOREIGN KEY person_experience_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: person_experience_experience (table: person_experience)
ALTER TABLE person_experience ADD CONSTRAINT person_experience_experience FOREIGN KEY person_experience_experience (experience_id)
REFERENCES experience (experience_id) ON DELETE CASCADE;

-- Reference: person_role_Person (table: person_role)
ALTER TABLE person_role ADD CONSTRAINT person_role_person FOREIGN KEY person_role_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: person_role_role (table: person_role)
ALTER TABLE person_role ADD CONSTRAINT person_role_role FOREIGN KEY person_role_role (role_id)
REFERENCES role (role_id) ON DELETE CASCADE;

-- Reference: user_Person (table: user)
ALTER TABLE user ADD CONSTRAINT user_person FOREIGN KEY user_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Reference: application_person (table: application)
ALTER TABLE application ADD CONSTRAINT application_person FOREIGN KEY application_person (person_id)
REFERENCES person (person_id) ON DELETE CASCADE;

-- Insert the values for the role.
INSERT INTO role (name) VALUES ("applicant");
INSERT INTO role (name) VALUES ("recruit");

-- End of file.