DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, '2020-01-30 10:00:00.0', 'Завтрак', 500),
  (100000, '2020-01-30 13:00:00.0', 'Обед', 1000),
  (100000, '2020-01-30 20:00:00.0', 'Ужин', 500),
  (100000, '2020-01-31 00:00:00.0', 'Еда на граничное значение', 100),
  (100000, '2020-01-31 10:00:00.0', 'Завтрак', 1000),
  (100000, '2020-01-31 13:00:00.0', 'Обед', 500),
  (100000, '2020-01-31 20:00:00.0', 'Ужин', 410);