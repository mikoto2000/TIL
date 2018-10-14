DROP TABLE IF EXISTS message;
DROP SEQUENCE IF EXISTS message_id_seq;

CREATE SEQUENCE message_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE message (
  id INTEGER DEFAULT nextval('message_id_seq') PRIMARY KEY,
  message VARCHAR(255) NOT NULL
);

INSERT INTO message(message)
    VALUES
        ('テスト１'),
        ('テスト２');

