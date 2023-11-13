drop table if exists account, account_type;

create table account_type (
    id bigserial primary key,
    type_name varchar(128) not null unique
);

create table account (
    id bigserial primary key,
    name varchar(128) not null unique,
    account_type_id bigint not null references account_type(id)
);
