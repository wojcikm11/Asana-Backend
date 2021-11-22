DROP TABLE IF EXISTS `favorites` ;
CREATE TABLE `favorites` (
	`userID` int,
    FOREIGN KEY (userID) REFERENCES user(ID),
    `projectID` int,
    FOREIGN KEY (projectID) REFERENCES project(ID)
    );
    

	
