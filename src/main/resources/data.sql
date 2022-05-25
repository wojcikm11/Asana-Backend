# haslo to psw
use asana;
INSERT INTO USER (email, password, name, enabled, locked)
VALUES ('john_doe@email.com', '$2a$12$bAuKcWsGdU3K9eGhvGjnReAO04Np.isPinCk21JJeaSd/5rQCzWDa', 'john98', 1, 0),
       ('alice_smith@email.com', '$2a$12$bAuKcWsGdU3K9eGhvGjnReAO04Np.isPinCk21JJeaSd/5rQCzWDa', 'pinky', 1, 0),
       ('megan_thee_stalion@email.com', '$2a$12$bAuKcWsGdU3K9eGhvGjnReAO04Np.isPinCk21JJeaSd/5rQCzWDa', 'holy_queen', 1,
        0),
       ('marie_sanchez@email.com', '$2a$12$bAuKcWsGdU3K9eGhvGjnReAO04Np.isPinCk21JJeaSd/5rQCzWDa', 'sanchezM', 1, 0),
       ('phoebe_watson@email.com', '$2a$12$bAuKcWsGdU3K9eGhvGjnReAO04Np.isPinCk21JJeaSd/5rQCzWDa', 'phoebe.watson', 1,
        0),
       ('adam_applebury@email.com', '$2a$12$bAuKcWsGdU3K9eGhvGjnReAO04Np.isPinCk21JJeaSd/5rQCzWDa', 'chuckNorris', 1,
        0);


INSERT INTO TEAM(name)
VALUES ('music team'),
       ('fullstack dep.'),
       ('bank team 2');

INSERT INTO TEAM_MEMBER(user_id, team_id, role)
VALUES (1, 1, 'OWNER'),
       (2, 2, 'OWNER'),
       (3, 3, 'OWNER'),
       (4, 1, 'OWNER'),
       (2, 1, 'MEMBER'),
       (4, 2, 'MEMBER'),
       (1, 3, 'MEMBER'),
       (6, 3, 'MEMBER'),
       (5, 3, 'MEMBER');

INSERT INTO PROJECT (name, category, description) VALUE
    ('Movie Assistant', 'Entertainment',
     'This service provides movies suggestions based on your internet activities'),
    ('Muzeer', 'Music', 'Music streaming service. Available on mobile and desktop'),
    ('PBK Bank App', 'Banking', 'Development of mobile app for the PBK Bank. Runs on Android and iOS'),
    ('CourseLand', 'Education', 'Platform for sharing and buying educational courses'),
    ('Better tomorrow', 'Ecology',
     'Platform that helps finding NGOs that do things you want to support. ');


INSERT INTO PROJECT_MEMBER(user_id, project_id, role)
VALUES (1, 1, 'OWNER'),
       (2, 1, 'MEMBER'),
       (4, 1, 'MEMBER'),
       (2, 2, 'OWNER'),
       (3, 3, 'OWNER'),
       (4, 4, 'OWNER'),
       (1, 2, 'MEMBER'),
       (4, 2, 'MEMBER'),
       (6, 3, 'MEMBER'),
       (6, 1, 'MEMBER'),
       (1, 3, 'MEMBER'),
       (2, 3, 'MEMBER'),
       (4, 3, 'MEMBER'),
       (2, 4, 'MEMBER'),
       (1, 4, 'MEMBER'),
       (3, 5, 'OWNER'),
       (1, 5, 'MEMBER'),
       (6, 5, 'MEMBER'),
       (5, 5, 'MEMBER');


INSERT INTO PROJECT_TEAM(team_id, project_id)
VALUES (1, 2),
       (1, 1),
       (1, 3),
       (1, 4),
       (3, 5);

INSERT INTO TASK(project_id, name, description, start_date, deadline, priority, status)
VALUES (1, 'Login page', 'Make page responsive, use Angular', '2022-02-12 00:00:00', '2022-02-20 23:15:30', 'LOW',
        'DOING'),
       (1, 'Database setup', null, '2022-02-01 10:00:00', '2022-02-13 23:15:30', 'HIGH', 'DONE'),
       (1, 'Server setup', null, '2022-01-19 10:00:00', '2022-02-13 23:15:30', 'LOW', 'DOING'),
       (1, 'Privacy policy ', 'Consult legal on privacy policy', '2022-01-01 13:00:00', '2022-01-10 13:15:30', 'LOW',
        'DONE'),
       (1, 'Registration page', null, '2022-01-13 00:00:00', '2022-01-20 16:15:30', 'MEDIUM', 'UNDONE'),
       (2, 'Database setup', null, '2022-01-01 22:50:00', '2022-01-05 13:15:30', 'HIGH', 'DONE'),
       (2, 'Contracts', 'Write contracts for artists what we want to have on our platform', '2021-12-12 11:20:00',
        '2022-02-13 23:15:30', 'HIGH', 'UNDONE'),
       (3, 'Servers setup', 'Design architecture of server connections', '2021-05-12 13:20:00',
        '2021-05-30 17:20:00', 'HIGH', 'UNDONE'),
       (3, 'API bux fix', 'Sending transfer is not working. Entity relationship error', '2021-06-01 13:20:00',
        '2021-06-15 17:20:00', 'MEDIUM', 'DOING'),
       (5, 'photos', 'pictures of animals on leaflets', '2021-07-01 10:00:00', '2021-07-30 23:15:30', 'MEDIUM',
        'DOING');


INSERT INTO TASK_ASSIGNEES(user_id, task_id, project_id)
VALUES (1, 1, 1),
       (2, 4, 2),
       (1, 6, 3),
       (1, 7, 3);

INSERT INTO PROJECT_MEMBER_TASK_TIME(user_id, task_id, project_id, time)
VALUES (1, 1, 1, 3600),
       (2, 4, 2, 89000),
       (1, 5, 2, 7200);






