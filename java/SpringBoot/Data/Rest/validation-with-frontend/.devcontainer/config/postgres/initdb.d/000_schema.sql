drop table if exists item_order, item, torder;

create table item (
    id bigserial primary key,
    item_name varchar(128) not null unique
);

create table torder (
    id bigserial primary key,
    order_name varchar(128) not null unique
);

create table item_order (
    id bigserial primary key,
    order_id bigint not null references torder(id),
    item_id bigint not null references item(id)
);
