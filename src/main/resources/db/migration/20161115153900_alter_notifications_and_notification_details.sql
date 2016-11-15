ALTER TABLE notifications RENAME TO notifications_old;
ALTER TABLE notification_details RENAME TO notification_details_old;

CREATE TABLE IF NOT EXISTS notifications (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_id     INTEGER,
    subject         VARCHAR(255) NOT NULL DEFAULT '',
    sent_at         TIMESTAMP
);


CREATE TABLE IF NOT EXISTS notification_details (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    notification_id INTEGER NOT NULL,
    email_address   VARCHAR(255) NOT NULL,
    read_count      INTEGER NOT NULL DEFAULT 0,

    FOREIGN KEY (notification_id)   REFERENCES notifications(id)
);

DROP TABLE notifications_old;
DROP TABLE notification_details_old;