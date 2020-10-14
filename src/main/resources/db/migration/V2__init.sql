drop sequence if exists hibernate_sequence;
drop table if exists roles cascade;
drop table if exists user_roles cascade;
drop table if exists users cascade;
drop table if exists projects cascade;
drop table  if exists project_developer cascade;

create sequence if not exists hibernate_sequence minvalue 50;

create table roles
(
    id   bigserial not null
        constraint roles_pkey
            primary key,
    role varchar(255)
);

create table users
(
    id       bigserial    not null
        constraint users_pkey
            primary key,
    email    varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    password varchar(255),
    username varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

create table user_roles
(
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);

create table projects
(
    id                 bigserial    not null
        constraint projects_pkey
            primary key,
    name               varchar(255) not null,
    project_manager_id bigint
        constraint fkgdyu73mg454kk9iys9567qft7
            references users
);

create table project_developer
(
    project_id   bigint not null
        constraint fkqif9vml6s9xk8s47ubtbra0l4
            references projects,
    developer_id bigint not null
        constraint fkqiwhu8sa1xf7yr4vayrcvqfxw
            references users,
    constraint project_developer_pkey
        primary key (project_id, developer_id)
);

insert into roles(id, role) values (1, 'ROLE_USER');
insert into roles(id, role) values (2, 'ROLE_MANAGER');
insert into roles(id, role) values (3, 'ROLE_DEVELOPER');

insert into users(id, email, password, username)
values(1, 'lamija@etf.unsa.ba', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'lamija');
insert into users(id, email, password, username)
values(2, 'denis@etf.unsa.ba', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'denis');
insert into users(id, email, password, username)
values(3, 'amila@etf.unsa.ba', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'amila');
insert into users(id, email, password, username)
values(4, 'ajsa@etf.unsa.ba', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'ajsa');
insert into users(id, email, password, username)
values(5, 'sara@etf.unsa.ba', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'sara');

insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (1, 2);
insert into user_roles (user_id, role_id) values (1, 3);
insert into user_roles (user_id, role_id) values (2, 1);
insert into user_roles (user_id, role_id) values (2, 2);
insert into user_roles (user_id, role_id) values (2, 3);
insert into user_roles (user_id, role_id) values (3, 1);
insert into user_roles (user_id, role_id) values (3, 3);
insert into user_roles (user_id, role_id) values (4, 1);
insert into user_roles (user_id, role_id) values (5, 1);

insert into projects (id, name, project_manager_id)
values (1, 'Bug Tracker', 1);
insert into projects (id, name, project_manager_id)
values (2, 'Blog', 1);
insert into projects (id, name, project_manager_id)
values (3, 'Reddit App', 2);

insert into project_developer (project_id, developer_id) values (1, 1);
insert into project_developer (project_id, developer_id) values (1, 2);
insert into project_developer (project_id, developer_id) values (1, 3);
insert into project_developer (project_id, developer_id) values (2, 1);
insert into project_developer (project_id, developer_id) values (3, 2);
insert into project_developer (project_id, developer_id) values (3, 3);