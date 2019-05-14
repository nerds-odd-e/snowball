CREATE TABLE  IF NOT EXISTS `public_questions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
   FOREIGN KEY (question_id)   REFERENCES public_questions(id),
  PRIMARY KEY (`id`));
