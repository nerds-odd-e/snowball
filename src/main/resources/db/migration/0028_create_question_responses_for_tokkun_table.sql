CREATE TABLE  IF NOT EXISTS `question_responses_for_tokkun` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  `counter` INT NOT NULL,
  `answered_at` DATETIME NOT NULL,
   FOREIGN KEY (question_id) REFERENCES questions(id),
   FOREIGN KEY (user_id) REFERENCES users(id),
  PRIMARY KEY (`id`));
