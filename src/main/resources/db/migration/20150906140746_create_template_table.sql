DROP TABLE IF EXISTS template;
CREATE TABLE IF NOT EXISTS template (
    Id              INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    TemplateName    VARCHAR(255) NOT NULL,
    Subject         VARCHAR(255),
    Content         NVARCHAR(5000)
);