DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, datetime, calories)VALUES
  (100000, 'завтрак', to_timestamp('24.07.2017 6:00','DD.MM.YYYY HH24:MI:SS'), 500),
  (100000, 'обед', to_timestamp('24.07.2017 13:00','DD.MM.YYYY HH24:MI:SS'), 1000),
  (100000, 'ужин', to_timestamp('24.07.2017 18:00','DD.MM.YYYY HH24:MI:SS'), 600),
  (100000, 'завтрак', to_timestamp('25.07.2017 6:00','DD.MM.YYYY HH24:MI:SS'), 400),
  (100000, 'обед', to_timestamp('25.07.2017 13:00','DD.MM.YYYY HH24:MI:SS'), 800),
  (100000, 'ужин', to_timestamp('25.07.2017 18:00','DD.MM.YYYY HH24:MI:SS'), 500);