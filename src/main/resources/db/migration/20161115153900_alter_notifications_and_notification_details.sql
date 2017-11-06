CREATE TABLE IF NOT EXISTS notifications (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_id     INTEGER,
    subject         VARCHAR(255) NOT NULL DEFAULT '',
    message_id BIGINT NOT NULL DEFAULT 0,
    sent_at         TIMESTAMP
);


CREATE TABLE IF NOT EXISTS notification_details (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    notification_id INTEGER NOT NULL,
    email_address   VARCHAR(255) NOT NULL,
    read_count      INTEGER NOT NULL DEFAULT 0,

    FOREIGN KEY (notification_id)   REFERENCES notifications(id)
);
