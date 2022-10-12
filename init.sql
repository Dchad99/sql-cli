create schema if not exists test;

create table if not exists test.company
(
    ID      SERIAL PRIMARY KEY,
    NAME    TEXT NOT NULL,
    AGE     INT  NOT NULL,
    ADDRESS CHAR(50),
    SALARY  REAL
)