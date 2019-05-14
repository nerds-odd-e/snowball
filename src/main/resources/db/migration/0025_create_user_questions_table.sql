CREATE TABLE  IF NOT EXISTS `user_questions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `user_id` INT NOT NULL,
   FOREIGN KEY (question_id)   REFERENCES public_questions(id),
   FOREIGN KEY (user_id)   REFERENCES users(id),
  PRIMARY KEY (`id`));
