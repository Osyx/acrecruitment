CREATE TABLE role (
role_id BIGINT PRIMARY KEY,
name VARCHAR(255)
);
CREATE TABLE person (
person_id BIGINT PRIMARY KEY,
name VARCHAR(255),
surname VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
role_id BIGINT REFERENCES role,
username VARCHAR(255)
);
CREATE TABLE availability (
availability_id BIGINT PRIMARY KEY,
person_id BIGINT REFERENCES person,
from_date DATE,
to_date DATE
);
CREATE TABLE competence (
competence_id BIGINT PRIMARY KEY,
name VARCHAR(255)
);
CREATE TABLE competence_profile (
competence_profile_id BIGINT PRIMARY KEY,
person_id BIGINT REFERENCES person,
competence_id BIGINT REFERENCES competence,
years_of_experience NUMERIC(4,2)
);