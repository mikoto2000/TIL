DROP TABLE IF EXISTS user1;

CREATE TABLE book
(
  id bigserial primary key,
  name varchar(40)
);


DROP TABLE IF EXISTS author;

CREATE TABLE author
(
  id bigserial primary key,
  name varchar(40),
);


DROP TABLE IF EXISTS book_author;

CREATE TABLE book_author
(
  book_id biginteger,
  author_id biginteger,
);

INSERT INTO book (name)
  VALUES
    ('book1'),
    ('book2'),
    ('book3'),
    ('book4'),
    ('book5')
    ;

INSERT INTO author (name)
  VALUES
    ('author1'),
    ('author2'),
    ('author3'),
    ('author4'),
    ('author5')
    ;

INSERT INTO book_author (book_id, author_id)
  VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 3),
    (3, 3),
    (3, 4),
    (4, 4),
    (4, 5),
    (5, 5)
    (5, 1)
    ;

