DROP TABLE IF EXISTS contact_people;
CREATE TABLE IF NOT EXISTS contact_people (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(50),
    email       VARCHAR(50),
    firstname    VARCHAR(50),
    lastname    VARCHAR(50),
    company     VARCHAR(50),
    location    VARCHAR(255) NOT NULL DEFAULT '',
    courses_sent    VARCHAR(255),
    date_sent       DATE,
    latitude DOUBLE NOT NULL DEFAULT 0,
    longitude DOUBLE NOT NULL DEFAULT 0,
    consent_request_date DATE,
    consent_received_date DATE
);

