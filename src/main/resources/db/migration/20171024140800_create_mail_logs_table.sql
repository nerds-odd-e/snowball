CREATE TABLE IF NOT EXISTS mail_logs (
    id                INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    contact_person_id INTEGER NOT NULL,
    course_id         INTEGER NOT NULL,
    sent_at           TIMESTAMP
);