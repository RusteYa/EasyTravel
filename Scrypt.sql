-- таблица регистрационных данных пользователя
CREATE TABLE login_data
(
  id              SERIAL       NOT NULL PRIMARY KEY,
  login           VARCHAR(30)  NOT NULL,
  hashed_password VARCHAR(100) NOT NULL,
  salt            VARCHAR(100) NOT NULL,
  email           VARCHAR(30),
  token           VARCHAR(100)
);
-- таблица данных профиля пользователя
CREATE TABLE profile
(
  id             SERIAL NOT NULL PRIMARY KEY,
  name           VARCHAR(20) DEFAULT '',
  surname        VARCHAR(20) DEFAULT '',
  fathers_name   VARCHAR(20) DEFAULT '',
  photo_path     VARCHAR(40) DEFAULT '',
  user_data_file TEXT        DEFAULT ''
);
-- таблица пользователя
CREATE TABLE users
(
  id            SERIAL NOT NULL PRIMARY KEY,
  login_data_id INTEGER REFERENCES login_data (id),
  profile_id    INTEGER REFERENCES profile (id)
);
-- таблица поездок
CREATE TABLE trip
(
  id   SERIAL      NOT NULL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);
-- таблица поездок пользователей
CREATE TABLE user_trip
(
  user_id INTEGER NOT NULL REFERENCES users (id),
  trip_id INTEGER NOT NULL REFERENCES trip (id),
  visited BOOLEAN NOT NULL DEFAULT FALSE
);
-- таблица топиков форума
CREATE TABLE topic
(
  id              SERIAL NOT NULL PRIMARY KEY,
  topicstarter_id INTEGER REFERENCES users (id),
  header          VARCHAR(40),
  likes           INTEGER,
  dislikes        INTEGER,
  content         TEXT,
  date            TIMESTAMP WITHOUT TIME ZONE,
  photo_path      VARCHAR(50)
);
-- таблица сообщений в топике
CREATE TABLE message
(
  id        SERIAL NOT NULL PRIMARY KEY,
  author_id INTEGER,
  date      TIMESTAMP WITHOUT TIME ZONE,
  header    VARCHAR(40),
  content   TEXT,
  topic_id  INTEGER REFERENCES topic (id)
);
-- таблица постов интересных мест для путешествий
CREATE TABLE place
(
  id         SERIAL  NOT NULL PRIMARY KEY,
  author_id  INTEGER NOT NULL REFERENCES users (id),
  photo_path VARCHAR(40),
  header     VARCHAR(40),
  content    TEXT,
  likes      INTEGER,
  dislikes   INTEGER,
  date       TIMESTAMP WITHOUT TIME ZONE
);
***************************************************************************************************************************
-- заполнение части таблиц начальными данными
INSERT INTO login_data (id, login, hashed_password, salt, email, token)
VALUES
  (1, 'admin', '39fd0beffb5e34ace3a011ca8e6260074a6846dcd305bcfbdb280d88f82f67d8', 'ж{Ш…СзYЩN9=Ћи•-ь',
   'admin@gmail.com',
   '');

INSERT INTO profile (id, name, surname, fathers_name, photo_path, user_data_file)
VALUES (1, 'admin', 'admin', 'admin', '/img/user.png', '');

INSERT INTO users (id, login_data_id, profile_id)
VALUES (1, 1, 1);

INSERT INTO place (author_id, photo_path, header, content, likes, dislikes, date)
VALUES (1, '/img/post.jpg', 'Заголовок', 'Тело сообщения', 0, 0, CURRENT_DATE);

INSERT INTO topic (id, topicstarter_id, header, likes, dislikes, content, date, photo_path)
VALUES (1, 1, 'Заголовок', 0, 0, 'Тело топика', current_date, '/img/post.jpg');

INSERT INTO trip (id, name) VALUES (1, 'Египет'), (2, 'Марокко');
***************************************************************************************************************************
-- индексы часто используемых запросов
CREATE INDEX login_data_login_index
  ON login_data (login);
CREATE INDEX login_data_id_index
  ON login_data (id);
CREATE INDEX profile_id_index
  ON profile (id);
CREATE INDEX users_id_index
  ON users (id);
CREATE INDEX message_id_index
  ON message (id);
CREATE INDEX message_topic_id_index
  ON message (topic_id);
CREATE INDEX place_id_index
  ON place (id);
CREATE INDEX place_author_id_index
  ON place (author_id);
CREATE INDEX topic_id_index
  ON topic (id);
-- индексы для отчетов
CREATE INDEX message_month_index
  ON message (EXTRACT(MONTH FROM date));
CREATE INDEX place_month_index
  ON place (EXTRACT(MONTH FROM date));
CREATE INDEX topic_dow_index
  ON topic (EXTRACT(DOW FROM date));
***************************************************************************************************************************
-- отчеты
CREATE VIEW messages_count_per_month AS
  SELECT
    EXTRACT(MONTH FROM date) AS "month",
    count(id)                AS "message_count"
  FROM message
  GROUP BY EXTRACT(MONTH FROM date);

CREATE VIEW place_count_per_month AS
  SELECT
    EXTRACT(MONTH FROM date) AS "month",
    count(id)                AS "place_count"
  FROM place
  GROUP BY EXTRACT(MONTH FROM date);

CREATE VIEW topic_count_per_day_of_week AS
  SELECT
    EXTRACT(DOW FROM date) AS "day_of_week",
    count(id)                AS "topic_count"
  FROM topic
  GROUP BY EXTRACT(DOW FROM date); 
***************************************************************************************************************************
-- логгирование добавлений, измений и удалений строк в ключевых таблицах
CREATE TABLE logs
(
  "text"  TEXT,
  "added" TIMESTAMP WITHOUT TIME ZONE
);

CREATE OR REPLACE FUNCTION users_add_to_log()
  RETURNS TRIGGER
AS $$
DECLARE
  str VARCHAR(300);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    str := 'Add new user ' || new.id || ' ' || new.login_data_id || ' ' || new.profile_id;
    INSERT INTO logs (text, added) VALUES (str, NOW());
    RETURN new;
  ELSIF TG_OP = 'UPDATE'
    THEN
      str := 'Update user ' || new.id || ' ' || new.login_data_id || ' ' || new.profile_id;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN new;
  ELSIF TG_OP = 'DELETE'
    THEN
      str := 'Delete user ' || old.id || ' ' || old.login_data_id || ' ' || old.profile_id;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN old;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION login_data_add_to_log()
  RETURNS TRIGGER
AS $$
DECLARE
  str VARCHAR(500);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    str :=
    'Add new login_data ' || new.id || ' ' || new.login || ' ' || new.hashed_password || ' ' || new.salt || ' ' ||
    new.email || ' ' || new.token;
    INSERT INTO logs (text, added) VALUES (str, NOW());
    RETURN new;
  ELSIF TG_OP = 'UPDATE'
    THEN
      str :=
      'Update login_data ' || new.id || ' ' || new.login || ' ' || new.hashed_password || ' ' || new.salt || ' ' ||
      new.email || ' ' || new.token;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN new;
  ELSIF TG_OP = 'DELETE'
    THEN
      str :=
      'Delete login_data ' || old.id || ' ' || old.login || ' ' || old.hashed_password || ' ' || old.salt || ' ' ||
      old.email || ' ' || old.token;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN old;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION profile_add_to_log()
  RETURNS TRIGGER
AS $$
DECLARE
  str VARCHAR(500);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    str :=
    'Add new profile ' || new.id || ' ' || new.name || ' ' || new.surname || ' ' || new.fathers_name || ' ' ||
    new.photo_path || ' ' || new.user_data_file;
    INSERT INTO logs (text, added) VALUES (str, NOW());
    RETURN new;
  ELSIF TG_OP = 'UPDATE'
    THEN
      str :=
      'Update profile ' || new.id || ' ' || new.name || ' ' || new.surname || ' ' || new.fathers_name || ' ' ||
      new.photo_path || ' ' || new.user_data_file;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN new;
  ELSIF TG_OP = 'DELETE'
    THEN
      str :=
      'Delete profile ' || old.id || ' ' || old.name || ' ' || old.surname || ' ' || old.fathers_name || ' ' ||
      old.photo_path || ' ' || old.user_data_file;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN old;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION message_add_to_log()
  RETURNS TRIGGER
AS $$
DECLARE
  str VARCHAR(500);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    str :=
    'Add new message ' || new.id || ' ' || new.author_id || ' ' || new.date || ' ' || new.header || ' ' ||
    new.content || ' ' || new.topic_id;
    INSERT INTO logs (text, added) VALUES (str, NOW());
    RETURN new;
  ELSIF TG_OP = 'UPDATE'
    THEN
      str :=
      'Update message ' || new.id || ' ' || new.author_id || ' ' || new.date || ' ' || new.header || ' ' ||
      new.content || ' ' || new.topic_id;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN new;
  ELSIF TG_OP = 'DELETE'
    THEN
      str :=
      'Delete message ' || old.id || ' ' || old.author_id || ' ' || old.date || ' ' || old.header || ' ' ||
      old.content || ' ' || old.topic_id;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN old;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION place_add_to_log()
  RETURNS TRIGGER
AS $$
DECLARE
  str VARCHAR(500);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    str :=
    'Add new place ' || new.id || ' ' || new.author_id || ' ' || new.photo_path || ' ' || new.header || ' ' ||
    new.content || ' ' || new.likes || ' ' || new.dislikes || ' ' || new.date;
    INSERT INTO logs (text, added) VALUES (str, NOW());
    RETURN new;
  ELSIF TG_OP = 'UPDATE'
    THEN
      str :=
      'Update place ' || new.id || ' ' || new.author_id || ' ' || new.photo_path || ' ' || new.header || ' ' ||
      new.content || ' ' || new.likes || ' ' || new.dislikes || ' ' || new.date;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN new;
  ELSIF TG_OP = 'DELETE'
    THEN
      str :=
      'Delete place ' || old.id || ' ' || old.author_id || ' ' || old.photo_path || ' ' || old.header || ' ' ||
      old.content || ' ' || old.likes || ' ' || old.dislikes || ' ' || old.date;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN old;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION topic_add_to_log()
  RETURNS TRIGGER
AS $$
DECLARE
  str VARCHAR(500);
BEGIN
  IF TG_OP = 'INSERT'
  THEN
    str :=
    'Add new topic ' || new.id || ' ' || new.topicstarter_id || ' ' || new.header || ' ' || new.likes || ' ' ||
    new.dislikes || ' ' || new.content || ' ' || new.date || ' ' || new.photo_path;
    INSERT INTO logs (text, added) VALUES (str, NOW());
    RETURN new;
  ELSIF TG_OP = 'UPDATE'
    THEN
      str :=
      'Update topic ' || new.id || ' ' || new.topicstarter_id || ' ' || new.header || ' ' || new.likes || ' ' ||
      new.dislikes || ' ' || new.content || ' ' || new.date || ' ' || new.photo_path;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN new;
  ELSIF TG_OP = 'DELETE'
    THEN
      str :=
      'Delete topic ' || old.id || ' ' || old.topicstarter_id || ' ' || old.header || ' ' || old.likes || ' ' ||
      old.dislikes || ' ' || old.content || ' ' || old.date || ' ' || old.photo_path;
      INSERT INTO logs (text, added) VALUES (str, NOW());
      RETURN old;
  END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER users_trigger
AFTER INSERT OR UPDATE OR DELETE ON users
FOR EACH ROW EXECUTE PROCEDURE users_add_to_log();

CREATE TRIGGER login_data_trigger
AFTER INSERT OR UPDATE OR DELETE ON login_data
FOR EACH ROW EXECUTE PROCEDURE login_data_add_to_log();

CREATE TRIGGER profile_trigger
AFTER INSERT OR UPDATE OR DELETE ON profile
FOR EACH ROW EXECUTE PROCEDURE profile_add_to_log();

CREATE TRIGGER message_trigger
AFTER INSERT OR UPDATE OR DELETE ON message
FOR EACH ROW EXECUTE PROCEDURE message_add_to_log();

CREATE TRIGGER place_trigger
AFTER INSERT OR UPDATE OR DELETE ON place
FOR EACH ROW EXECUTE PROCEDURE place_add_to_log();

CREATE TRIGGER topic_trigger
AFTER INSERT OR UPDATE OR DELETE ON topic
FOR EACH ROW EXECUTE PROCEDURE topic_add_to_log();
***************************************************************************************************************************
-- создание денормализованной таблицы пользователя и процедура ее заполнения
CREATE TABLE denorm_users
(
  id              SERIAL       NOT NULL PRIMARY KEY,
  login           VARCHAR(30)  NOT NULL,
  hashed_password VARCHAR(100) NOT NULL,
  salt            VARCHAR(100) NOT NULL,
  email           VARCHAR(30),
  token           VARCHAR(100),
  name            VARCHAR(20),
  surname         VARCHAR(20),
  fathers_name    VARCHAR(20),
  photo_path      VARCHAR(40),
  user_data_file  TEXT
);

CREATE OR REPLACE FUNCTION denorm_users_fill()
  RETURNS VOID
AS $$
BEGIN
  INSERT INTO denorm_users (id, login, hashed_password, salt, email, token, name, surname, fathers_name, photo_path, user_data_file)
    SELECT
      users.id AS id,
      login,
      hashed_password,
      salt,
      email,
      token,
      name,
      surname,
      fathers_name,
      photo_path,
      user_data_file
    FROM users
      JOIN login_data ON users.login_data_id = login_data.id
      JOIN profile ON users.profile_id = profile.id;
END;
$$
LANGUAGE plpgsql;

SELECT denorm_users_fill();
