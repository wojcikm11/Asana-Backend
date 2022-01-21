use asana;


CREATE TABLE IF NOT EXISTS user (
	id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(30) NOT NULL,
    enabled BOOLEAN,
    locked BOOLEAN
);

CREATE TABLE IF NOT EXISTS token (
	id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    confirmed_at DATETIME,
    user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user (id)
    
    
);

CREATE TABLE IF NOT EXISTS project (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    category ENUM('IT'),
    description VARCHAR(200),
	UNIQUE (id, name)
);

CREATE TABLE IF NOT EXISTS team (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS team_member (
	user_id INT NOT NULL,
    team_id INT NOT NULL,
    role ENUM('owner', 'member') NOT NULL,
    PRIMARY KEY (user_id, team_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE IF NOT EXISTS project_team (
    team_id INT NOT NULL,
    project_id INT NOT NULL,
    PRIMARY KEY (team_id, project_id),
    FOREIGN KEY (team_id) REFERENCES team (id),
    FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE IF NOT EXISTS favorites (
	user_id INT NOT NULL,
    project_id INT NOT NULL,
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE IF NOT EXISTS project_member (
	user_id INT NOT NULL,
    project_id INT NOT NULL,
    role VARCHAR(20),
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE IF NOT EXISTS task (
	id INT PRIMARY KEY AUTO_INCREMENT,
    project_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
	description VARCHAR(200),
	start_date DATETIME NOT NULL,
	deadline DATETIME NOT NULL,
    priority ENUM('Low', 'Medium', 'High') NOT NULL,
    status ENUM('In_progress', 'Finished') NOT NULL DEFAULT 'In_progress',
    FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE IF NOT EXISTS task_assignees (
	user_id INT NOT NULL,
	task_id INT NOT NULL,
	PRIMARY KEY (user_id, task_id),
	FOREIGN KEY (user_id) REFERENCES user (id),
	FOREIGN KEY (task_id) REFERENCES task (id)
);

CREATE TABLE IF NOT EXISTS subtask (
	id INT PRIMARY KEY AUTO_INCREMENT,
    task_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
	description VARCHAR(200),
	start_date DATETIME NOT NULL,
	deadline DATETIME NOT NULL,
    priority ENUM('Low', 'Medium', 'High') NOT NULL,
    status ENUM('In_progress', 'Finished') NOT NULL DEFAULT 'In_progress',
    FOREIGN KEY (task_id) REFERENCES task (id)
);

CREATE TABLE IF NOT EXISTS subtask_assignees (
	user_id INT NOT NULL,
	subtask_id INT NOT NULL,
	PRIMARY KEY (user_id, subtask_id),
	FOREIGN KEY (user_id) REFERENCES user (id),
	FOREIGN KEY (subtask_id) REFERENCES subtask (id)
);

CREATE TABLE IF NOT EXISTS message (
	id INT PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
    project_id INT NOT NULL,
    message VARCHAR(1000) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (project_id) REFERENCES project (id)
);
