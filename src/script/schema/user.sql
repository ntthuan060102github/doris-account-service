CREATE TABLE `user` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(100) NOT NULL,
	`first_name` VARCHAR(100) NOT NULL,
	`last_name` VARCHAR(100) NOT NULL,
	`password` TEXT NOT NULL,
	`birthday` DATE NULL DEFAULT NULL,
	`avatar_url` VARCHAR(500) NULL DEFAULT NULL,
	`phone_number` VARCHAR(15) NULL DEFAULT NULL,
	`gender` TINYINT(3) NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `email` (`email`) USING BTREE,
	CONSTRAINT `user_chk_gender_range` CHECK (((`gender` >= -(1)) and (`gender` <= 1)))
);
