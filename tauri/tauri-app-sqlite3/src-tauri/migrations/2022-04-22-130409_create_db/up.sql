CREATE TABLE channel (
  id INTEGER NOT NULL PRIMARY KEY,
  uri VARCHAR NOT NULL UNIQUE,
  name VARCHAR NOT NULL
);

CREATE TABLE episode (
  id INTEGER NOT NULL PRIMARY KEY,
  channel_id INTEGER NOT NULL,
  title VARCHAR NOT NULL,
  uri VARCHAR NOT NULL
);
