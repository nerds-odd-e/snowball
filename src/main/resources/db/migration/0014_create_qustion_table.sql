CREATE TABLE `questions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(500) NOT NULL,
  `is_multi_question` TINYINT NOT NULL,
  `advice` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`));
