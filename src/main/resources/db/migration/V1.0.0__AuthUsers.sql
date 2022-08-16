CREATE TABLE Users
(
    id            SERIAL NOT NULL,
    first_name    VARCHAR(30),
    last_name     VARCHAR(30),
    login         VARCHAR(15),
    password      VARCHAR(64),
    access_status VARCHAR(20)
);
INSERT INTO Users (first_name, last_name, login, password, access_status)
VALUES ( 'Kirill', 'Pyatyshev', 'admin', 'admin', 'admin'),
       ( 'Bob', 'Pinger', 'user', 'user', 'user');