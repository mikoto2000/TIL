drop table if exists book_master_authors;
drop table if exists author;
drop table if exists book_master;

create table book_master (
  id bigserial primary key,
  name varchar(128)
);

create table author (
  id bigserial primary key,
  name varchar(128)
);

create table book_master_author (
  id bigserial primary key,
  book_master_id bigint,
  author_id bigint,
  constraint bmar_bm foreign key (book_master_id) references book_master (id),
  constraint bmar_a foreign key (author_id) references author (id)
)

