CREATE TABLE  IF NOT EXISTS `question_responses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `test_id` VARCHAR(500)  NOT NULL,
  `question_id` INT NOT NULL,
  `is_answer_correct` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`));

