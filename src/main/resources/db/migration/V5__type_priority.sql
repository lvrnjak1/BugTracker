drop table if exists ticket_priorities;
drop table if exists ticket_types;

create table ticket_priorities
(
    id   bigserial not null
        constraint ticket_priorities_pkey
            primary key,
    priority varchar(255)
);

create table ticket_types
(
    id   bigserial not null
        constraint ticket_types_pkey
            primary key,
    type varchar(255)
);

insert into ticket_priorities(id, priority) values(1, 'LOW');
insert into ticket_priorities(id, priority) values(2, 'MIDDLE');
insert into ticket_priorities(id, priority) values(3, 'HIGH');

insert into ticket_types(id, type) values (1, 'BUG');
insert into ticket_types(id, type) values (2, 'FEATURE');
insert into ticket_types(id, type) values (3, 'ENHANCEMENT');