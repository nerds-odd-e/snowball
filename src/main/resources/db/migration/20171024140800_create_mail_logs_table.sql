CREATE TABLE IF NOT EXISTS course_contact_notifications (
    id                INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    contact_person_id INTEGER NOT NULL,
    course_id         INTEGER NOT NULL,
    sent_at           TIMESTAMP
);