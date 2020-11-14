create schema app;

CREATE SEQUENCE app.seq_country start 4;

create table app.country
(
    id       integer default nextval('app.seq_country') primary key,
    name     varchar(255) not null unique
);

insert into app.country (id, name)
values (1, 'United Kingdom'),
       (2, 'Unated States'),
       (3, 'Netherlands');
