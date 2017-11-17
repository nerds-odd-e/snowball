
CREATE TABLE IF NOT EXISTS courses (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    coursename        VARCHAR(255) NOT NULL,
    duration VARCHAR(100),
    location    VARCHAR(255) NOT NULL DEFAULT '',
    startdate  DATE,
    address  VARCHAR(1500) NOT NULL DEFAULT '',
    coursedetails  VARCHAR(2000) NOT NULL DEFAULT '',
    instructor VARCHAR(255) NOT NULL DEFAULT '',
    latitude DOUBLE NOT NULL DEFAULT 0,
    longitude DOUBLE NOT NULL DEFAULT 0
);