insert into account_type (id, name)
values
    (1, '管理者'),
    (2, '一般');

insert into account (id, name, account_type_id)
values
    (1, 'mikoto2000', 1),
    (2, 'makoto2000', 2);