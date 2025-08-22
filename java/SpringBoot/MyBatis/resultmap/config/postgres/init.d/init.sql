DROP TABLE IF EXISTS "user";

CREATE TABLE "user"
(
  id SERIAL PRIMARY KEY,
  name varchar(40)
);


DROP TABLE IF EXISTS user_info;

CREATE TABLE user_info
(
  id SERIAL PRIMARY KEY,
  user_id int NOT NULL,
  description varchar(100),
  FOREIGN KEY (user_id) REFERENCES "user"(id)
);

INSERT INTO
  "user" (id, name)
VALUES
  (1, 'user1'),
  (2, 'user2');

INSERT INTO
  user_info (id, user_id, description)
VALUES
  (1, 1, 'user1 info1'),
  (2, 1, 'user1 info2'),
  (3, 1, 'user1 info3'),
  (4, 2, 'user2 info1'),
  (5, 2, 'user2 info2'),
  (6, 2, 'user2 info3');



