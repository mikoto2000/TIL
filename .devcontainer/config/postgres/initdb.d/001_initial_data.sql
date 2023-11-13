insert into account_type (id, type_name)
values
    (1, 'admin'),
    (2, 'member');

insert into account (id, name, account_type_id)
values
    (1, 'd', 1),
    (2, 'c', 2),
    (3, 'b', 1),
    (4, 'a', 2);