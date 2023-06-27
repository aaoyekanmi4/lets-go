drop database if exists lets_go;
create database lets_go;

use lets_go;

drop table if exists app_user_role;
drop table if exists app_role;
drop table if exists app_user;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    email varchar(50) not null unique,
    phone varchar(50) not null unique,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"

insert into app_user (username, password_hash, enabled, email, phone, first_name, last_name)
    values
    ('eric@dev10.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1, 'eric@dev10.com', '9672254902', 'Eric', 'Baffour-Addo'),
    ('jay@dev10.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1, 'jay@dev10.com', '9872254902', 'Jay', 'McGary'),
    ('arit@dev10.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1, 'arit@dev10.com', '9877254902', 'Arit', 'Oyekan');

insert into app_user_role
    values
    (1, 1),
    (2, 1),
    (3, 1);