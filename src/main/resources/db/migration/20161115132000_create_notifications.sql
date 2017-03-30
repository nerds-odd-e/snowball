CREATE TABLE IF NOT EXISTS notifications (
    id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_id INTEGER NOT NULL,
    sent_at     TIMESTAMP,

    FOREIGN KEY (template_id)   REFERENCES Template(Id)
);

CREATE TABLE IF NOT EXISTS notification_details (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    notification_id INTEGER NOT NULL,
    contact_id      INTEGER NOT NULL,
    mail_read_count INTEGER NOT NULL DEFAULT 0,

    FOREIGN KEY (notification_id)   REFERENCES notifications(id),
    FOREIGN KEY (contact_id)        REFERENCES mail(id)
);