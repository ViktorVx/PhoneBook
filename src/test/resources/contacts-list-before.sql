truncate contact cascade;

insert into contact(id, first_name, last_name, user_id) values
(1, 'Ivan', 'Ivanov', 1),
(2, 'Sidor', 'Sidorov', 1),
(3, 'Petr', 'Petrov', 1),
(4, 'Bred', 'Pitt', 2),
(5, 'Bill', 'Gates', 2),
(6, 'Mickle', 'Jackson', 2),
(7, 'Ivan', 'Mikrob', 1);

alter sequence hibernate_sequence restart with 10;