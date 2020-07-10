CREATE DATABASE tasksDB ENCODING 'UTF-8';

--Задачи.
CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(50)  NOT NULL,
    description VARCHAR(250) null,
    date        timestamp    not null,
    isdone      boolean      not null

);

--Пользователи.
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(10) UNIQUE NOT NULL,
    password VARCHAR(10) UNIQUE NOT NULL

);
