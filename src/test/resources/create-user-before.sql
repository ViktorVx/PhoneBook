truncate user_role, usr cascade;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$b9tbD1glu2OzkQlQmfR8iOgt19UVvIfxxrfW.vAeX0Q2ARBPjrg4O', 'admin'),
(2, true, '$2a$08$b9tbD1glu2OzkQlQmfR8iOgt19UVvIfxxrfW.vAeX0Q2ARBPjrg4O', 'user');


insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'), (2, 'USER');
