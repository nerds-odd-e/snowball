CREATE TABLE IF NOT EXISTS Template (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    TemplateName    VARCHAR(255) NOT NULL,
    Subject         VARCHAR(255),
    Content         NVARCHAR(5000)
);