CREATE TABLE IF NOT EXISTS notifications (
    id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_id INTEGER NOT NULL,
    subject         VARCHAR(255) NOT NULL DEFAULT '',
    sent_at     TIMESTAMP,
    notification_id INTEGER NOT NULL DEFAULT 0

);

CREATE TABLE IF NOT EXISTS notification_details (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    notification_id INTEGER NOT NULL,
    email_address   VARCHAR(255) NOT NULL,
    read_count      INTEGER NOT NULL DEFAULT 0,

    FOREIGN KEY (notification_id)   REFERENCES notifications(id)
);