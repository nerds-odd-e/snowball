CREATE TABLE IF NOT EXISTS templates (
    id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    template_name   VARCHAR(255) NOT NULL,
    subject         VARCHAR(255),
    content         NVARCHAR(5000)
);