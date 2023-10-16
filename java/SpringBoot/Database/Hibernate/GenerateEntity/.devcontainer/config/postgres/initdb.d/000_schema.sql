drop table if exists application_user;

create table application_user (
    id serial primary key,
    name varchar(255),
    create_at timestamp default now(),
    create_by varchar(255),
    update_at timestamp default now(),
    update_by varchar(255)
);

