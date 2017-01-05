CREATE TABLE `fang` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`keyx` VARCHAR(50) NOT NULL,
	`title` VARCHAR(50) NOT NULL,
	`nums` VARCHAR(50) NOT NULL,
	`money` CHAR(50) NOT NULL,
	`vendor` VARCHAR(50) NOT NULL,
	`start_time` DATETIME NOT NULL,
	`end_time` DATETIME NOT NULL,
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`proxy` VARCHAR(50) NOT NULL,
	`proxy_user_name` VARCHAR(50) NOT NULL,
	`proxy_user_tel` VARCHAR(50) NOT NULL,
	`comment` VARCHAR(500) NOT NULL DEFAULT '',
	`focus` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=49
;
