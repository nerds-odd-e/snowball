ALTER TABLE user_questions DROP FOREIGN KEY `user_questions_ibfk_1`;
ALTER TABLE user_questions ADD FOREIGN KEY (question_id) REFERENCES questions(id);
