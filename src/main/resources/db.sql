CREATE DATABASE tasksDB ENCODING 'UTF-8';

--Задачи.
CREATE TABLE IF NOT EXISTS tasks
(
    id      SERIAL PRIMARY KEY,
    userId      INT NOT NULL,
    goalId      INT,
    title       VARCHAR(50)  NOT NULL,
    description VARCHAR(250) null,
    date        timestamp    not null,
    isDone      boolean      not null

);

--Пользователи.
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(10) UNIQUE NOT NULL,
    password VARCHAR(10)  NOT NULL

);

--Цели
CREATE TABLE IF NOT EXISTS goals
(
    id      SERIAL PRIMARY KEY,
    title       VARCHAR(50)  NOT NULL,
    description VARCHAR(250) null,
    userId      INT NOT NULL
);
