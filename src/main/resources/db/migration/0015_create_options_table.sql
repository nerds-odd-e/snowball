CREATE TABLE  IF NOT EXISTS `options` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `is_correct` TINYINT NOT NULL,
  PRIMARY KEY (`id`));
