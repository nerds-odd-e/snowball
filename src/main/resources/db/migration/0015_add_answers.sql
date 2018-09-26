create table if NOT EXISTS answers(
    id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    question_id INTEGER NOT NULL,
    body        VARCHAR(200) NOT NULL,
    correct     boolean,
    FOREIGN KEY (question_id) REFERENCES questions(id)
)
