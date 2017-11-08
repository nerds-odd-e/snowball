CREATE TABLE IF NOT EXISTS sent_mails (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_id     INTEGER,
    subject         VARCHAR(255) NOT NULL DEFAULT '',
    content            TEXT,
    message_id BIGINT NOT NULL DEFAULT 0,
    sent_at         TIMESTAMP
);


CREATE TABLE IF NOT EXISTS sent_mail_visits (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    sent_mail_id INTEGER NOT NULL,
    email_address   VARCHAR(255) NOT NULL,
    read_count      INTEGER NOT NULL DEFAULT 0,

    FOREIGN KEY (sent_mail_id)   REFERENCES sent_mails(id)
);
