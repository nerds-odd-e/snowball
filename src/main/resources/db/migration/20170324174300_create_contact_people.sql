CREATE TABLE IF NOT EXISTS contact_people (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name        VARCHAR(50),
    email       VARCHAR(50),
    firstname    VARCHAR(50),
    lastname    VARCHAR(50),
    company     VARCHAR(50),
    location    VARCHAR(255) NOT NULL DEFAULT '',
    courses_sent    VARCHAR(255),
    date_sent       DATETIME,
    latitude DOUBLE NOT NULL DEFAULT 0,
    longitude DOUBLE NOT NULL DEFAULT 0
);
ALTER TABLE contact_people ADD latitude DOUBLE NOT NULL DEFAULT 0;
ALTER TABLE contact_people ADD longitude DOUBLE NOT NULL DEFAULT 0;
