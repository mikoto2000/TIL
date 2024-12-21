insert into ndc_category (id, name)
values
  (1, 'category1'),
  (2, 'category2'),
  (3, 'category3')
;

insert into author (id, name)
values
  (1, 'author1'),
  (2, 'author2'),
  (3, 'author3'),
  (4, 'author4'),
  (5, 'author5')
;

insert into book_master (id, name, publication_date, ndc_category_id)
values
  (1, 'book1', '2024-12-19', 1),
  (2, 'book2', '2024-12-20', 2),
  (3, 'book3', '2024-12-21', 2),
  (4, 'book4', '2024-12-22', 2),
  (5, 'book5', '2024-12-23', 1)
;

insert into book_master_author (id, book_master_id, author_id)
values
  (1, 1, 1),
  (2, 1, 2),
  (3, 1, 3),
  (4, 1, 4),
  (5, 1, 5),
  (6, 2, 2),
  (7, 3, 3),
  (8, 4, 4),
  (9, 5, 1),
  (10, 5, 5)
;
