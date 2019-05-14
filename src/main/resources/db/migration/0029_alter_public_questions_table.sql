ALTER TABLE public_questions DROP FOREIGN KEY `public_questions_ibfk_1`;
ALTER TABLE public_questions ADD FOREIGN KEY (question_id) REFERENCES questions(id);
