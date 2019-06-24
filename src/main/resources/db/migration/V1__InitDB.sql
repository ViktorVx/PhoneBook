 alter table if exists contact drop constraint if exists contact_user_fk;

 alter table if exists user_role drop constraint if exists user_role_user_fk;

 drop table if exists contact CASCADE;

 drop table if exists user_role cascade;

 drop table if exists usr cascade;

 drop sequence if exists hibernate_sequence;

 create sequence hibernate_sequence start 1 increment 1;

 create table contact (
    id int8 not null,
    first_name varchar(255),
    last_name varchar(255),
    photo_path varchar(255),
    user_id int8,
    primary key (id)
 );

 create table user_role (
    id int8 not null,
    user_id int8 not null,
    roles varchar(255)
 );

 create table usr (
    id int8 not null,
    active boolean not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
 );

alter table if exists contact add constraint contact_user_fk foreign key (user_id) references usr;
alter table if exists user_role add constraint user_role_user_fk foreign key (user_id) references usr;

insert into usr (id, active, "password", username) values
    (1, true, '1', 'admin'),
    (2, true, '123', 'user');

insert into user_role (id, user_id, roles) values
    (1, 1, 'ADMIN'),
    (2, 2, 'USER');