drop table if exists accounts;
drop table if exists roles;

create table roles (
  id bigserial primary key,
  name varchar(128)
);

create table accounts (
  id bigserial primary key,
  name varchar(128),
  role_id bigint,
  constraint account_role foreign key (role_id) references roles (id)
);

