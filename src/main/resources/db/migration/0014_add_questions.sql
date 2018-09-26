create table if NOT EXISTS questions(
    id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    body       VARCHAR(200) NOT NULL,
    advice     VARCHAR(100)
)
