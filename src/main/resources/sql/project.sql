DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
	`ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(40) NOT NULL,
    `category` varchar(40) DEFAULT NULL
    )
   
    