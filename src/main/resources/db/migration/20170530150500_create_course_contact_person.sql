CREATE TABLE IF NOT EXISTS COURSE_CONTACT_PERSON (
    id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    course_id   INTEGER NOT NULL,
    contact_person_id INTEGER NOT NULL,

    FOREIGN KEY (course_id)   REFERENCES courses(id),
    FOREIGN KEY (contact_person_id)   REFERENCES contact_people(id)
);

--select * from COURSE_CONTACT_PERSON;