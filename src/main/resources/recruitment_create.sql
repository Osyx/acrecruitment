-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2018-01-29 10:52:02.892

-- tables
-- Table: person
CREATE TABLE person (
    person_id bigint NOT NULL,
    name varchar(40) NOT NULL,
    surname varchar(40) NOT NULL,
    ssn varchar(40) NOT NULL,
    email varchar(40) NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY (person_id)
);

-- Table: availability
CREATE TABLE availability (
    availability_id bigint NOT NULL,
    person_id bigint NOT NULL,
    from_date date NOT NULL,
    to_date date NOT NULL,
    CONSTRAINT availability_pk PRIMARY KEY (availability_id)
);

-- Table: experience
CREATE TABLE experience (
    experience_id bigint NOT NULL,
    name varchar(40) NOT NULL,
    CONSTRAINT experience_pk PRIMARY KEY (experience_id)
);

-- Table: person_experience
CREATE TABLE person_experience (
    person_experience_id bigint NOT NULL,
    person_id bigint NOT NULL,
    experience_id bigint NOT NULL,
    years_of_experience double(4,2) NOT NULL,
    CONSTRAINT person_experience_pk PRIMARY KEY (person_experience_id)
);

-- Table: person_role
CREATE TABLE person_role (
    person_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT person_role_pk PRIMARY KEY (person_id,role_id)
);

-- Table: role
CREATE TABLE role (
    role_id bigint NOT NULL,
    name varchar(40) NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (role_id)
);

-- Table: user
CREATE TABLE user (
    user_id bigint NOT NULL,
    person_id bigint NOT NULL,
    username varchar(40) NOT NULL,
    password varchar(40) NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);

-- foreign keys
-- Reference: availability_Person (table: availability)
ALTER TABLE availability ADD CONSTRAINT availability_Person FOREIGN KEY availability_Person (person_id)
    REFERENCES person (person_id);

-- Reference: person_experience_Person (table: person_experience)
ALTER TABLE person_experience ADD CONSTRAINT person_experience_Person FOREIGN KEY person_experience_Person (person_id)
    REFERENCES person (person_id);

-- Reference: person_experience_experience (table: person_experience)
ALTER TABLE person_experience ADD CONSTRAINT person_experience_experience FOREIGN KEY person_experience_experience (experience_id)
    REFERENCES experience (experience_id);

-- Reference: person_role_Person (table: person_role)
ALTER TABLE person_role ADD CONSTRAINT person_role_Person FOREIGN KEY person_role_Person (person_id)
    REFERENCES person (person_id);

-- Reference: person_role_role (table: person_role)
ALTER TABLE person_role ADD CONSTRAINT person_role_role FOREIGN KEY person_role_role (role_id)
    REFERENCES role (role_id);

-- Reference: user_Person (table: user)
ALTER TABLE user ADD CONSTRAINT user_Person FOREIGN KEY user_Person (person_id)
    REFERENCES person (person_id);

-- End of file.

