CREATE USER 'taskmanager'@'localhost' IDENTIFIED BY 'taskmanager';
GRANT ALL PRIVILEGES ON * . * TO 'taskmanager'@'localhost';
ALTER USER 'taskmanager'@'localhost' IDENTIFIED WITH mysql_native_password BY 'awesomepassword';