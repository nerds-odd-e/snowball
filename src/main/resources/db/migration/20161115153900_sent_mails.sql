CREATE TABLE IF NOT EXISTS sent_mails (
    id              INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    template_id     INTEGER NOT NULL,
    receivers       VARCHAR(255) NOT NULL DEFAULT '',
    subject         VARCHAR(255) NOT NULL DEFAULT '',
    content            TEXT,
    message_id BIGINT NOT NULL DEFAULT 0,
    sent_at         TIMESTAMP
);


CREATE TABLE IF NOT EXISTS sent_mail_visits (
    id              INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    sent_mail_id INTEGER NOT NULL,
    email_address   VARCHAR(255) NOT NULL,
    read_count      INTEGER NOT NULL DEFAULT 0,

    FOREIGN KEY (sent_mail_id)   REFERENCES sent_mails(id)
);

ALTER TABLE sent_mails MODIFY template_id INTEGER null;

#ALTER TABLE sent_mails ADD COLUMN receivers VARCHAR(255) NOT NULL DEFAULT '';
