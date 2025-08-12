CREATE TABLE account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL);
