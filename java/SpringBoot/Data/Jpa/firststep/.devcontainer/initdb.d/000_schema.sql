drop table if exists Account;
drop table if exists Role;

create table Role (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null
);

create table Account (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null,
  role bigint not null,
  foreign key (role) references Role (id)
);

