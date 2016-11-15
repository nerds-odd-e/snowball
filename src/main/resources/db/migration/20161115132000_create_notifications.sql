DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS notification_details;

CREATE TABLE IF NOT EXISTS notifications (
    id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_id INTEGER NOT NULL,
    sent_at     TIMESTAMP
);

CREATE TABLE IF NOT EXISTS notification_details (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    notification_id INTEGER NOT NULL,
    contact_id      INTEGER NOT NULL,
    mail_read_count INTEGER NOT NULL DEFAULT 0
);