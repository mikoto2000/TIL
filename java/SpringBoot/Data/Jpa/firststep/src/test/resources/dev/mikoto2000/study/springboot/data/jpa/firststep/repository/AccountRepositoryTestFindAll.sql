delete from Role;
delete from Account;

insert into Role
  (id, name)
values
  (1, 'admin'),
  (2, 'user'),
  (3, 'developer');

insert into Account
  (id, name, role)
values
  (1, 'mikoto2000', 1),
  (2, 'makoto2000', 2),
  (3, 'mokoto2000', 3);

