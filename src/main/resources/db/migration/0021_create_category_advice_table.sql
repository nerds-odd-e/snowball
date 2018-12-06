CREATE TABLE  IF NOT EXISTS `category_advices` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_id` INT unsigned NOT NULL,
  `advice` TEXT NOT NULL,
  PRIMARY KEY (`id`));