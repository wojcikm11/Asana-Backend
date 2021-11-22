DROP TABLE IF EXISTS `user`;
CREATE TABLE `User` (
	`ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`email` varchar(50) NOT NULL,
	`password` varchar(45) NOT NULL,
	`full_name` varchar(50) NOT NULL
		);




