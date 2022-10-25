create schema if not exists test;

create table if not exists test.company
(
    ID      SERIAL PRIMARY KEY,
    NAME    TEXT NOT NULL,
    AGE     INT  NOT NULL,
    ADDRESS CHAR(50),
    SALARY  REAL
);

insert into test.company(name, age, address, salary) values ('DRC', 22, 'Melitopol', 12600);
insert into test.company(name, age, address, salary) values ('Royal TD', 28, 'Kyiv', 12000);
insert into test.company(name, age, address, salary) values ('TLK', 27, 'Krakov', 16400);
insert into test.company(name, age, address, salary) values ('Heineken', 22, 'Berlin', 12610);
insert into test.company(name, age, address, salary) values ('Tesco', 22, 'London', 14600);