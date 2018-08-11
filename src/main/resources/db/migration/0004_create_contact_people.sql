CREATE TABLE IF NOT EXISTS contact_people (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(50),
    email       VARCHAR(50),
    firstname    VARCHAR(50),
    lastname    VARCHAR(50),
    company     VARCHAR(50),
    location    VARCHAR(255) NOT NULL DEFAULT '',
    consent_id  varchar(64) default null,
    courses_sent    VARCHAR(255),
    date_sent       DATE,
    latitude DOUBLE NOT NULL DEFAULT 0,
    longitude DOUBLE NOT NULL DEFAULT 0
);

