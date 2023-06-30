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

create table venue (
	venue_id int primary key auto_increment,
    venue_name varchar(100) not null,
    address varchar(100) not null,
    city varchar(100) not null,
    state varchar(100) null,
    country varchar(100) null,
    zipcode int not null
);

create table `event` (
	event_id int primary key auto_increment,
	event_name varchar(100) not null,
    category varchar(50) null,
    image_url varchar(250) null,
    `description` text,
    event_date datetime not null,
    `source` varchar(50) null,
    source_id varchar(100) null,
    event_link varchar(250) null,
    venue_id int not null,
    constraint fk_event_venue_id
		foreign key (venue_id)
		references venue(venue_id),
	constraint uq_source_source_id
         unique (source, source_id)
);

create table saved_event (
    saved_event_id int primary key auto_increment,
	event_id int not null,
    app_user_id int not null,
    constraint fk_saved_event_event_id
		foreign key (event_id)
		references `event`(event_id),
	constraint fk_saved_event_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id)    
);

create table event_post (
	event_post_id int primary key auto_increment,
	event_id int not null,
    app_user_id int not null,
    author varchar(50) not null, 
    post_date datetime not null,
    post_body varchar(500) not null,
    likes int not null,
    constraint fk_event_post_event_id
		foreign key (event_id)
		references `event`(event_id),
	constraint fk_event_post_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id)    
);

create table contact (
	contact_id int primary key auto_increment,
    app_user_id int not null,
    email varchar(50) not null,
    phone varchar(50) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
	constraint fk_contact_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),   
	constraint uq_app_user_id_email
        unique (app_user_id, email)
);

create table `group` (
	group_id int primary key auto_increment,
    group_name varchar(50) not null,
    app_user_id int not null,
	constraint fk_group_app_user_id
		foreign key (app_user_id)
		references app_user(app_user_id)   
);

create table group_contact (
	group_id int not null,
    contact_id int not null,
	constraint pk_group_contact
        primary key (group_id, contact_id),
    constraint fk_group_contact_group_id
		foreign key (group_id)
		references `group`(group_id),
	constraint fk_group_contact_contact_id
		foreign key (contact_id)
		references contact(contact_id)    
);

create table group_saved_event (
	group_id int not null,
    saved_event_id int not null,
	constraint pk_group_saved_event
        primary key (group_id, saved_event_id),
    constraint fk_group_saved_event_group_id
		foreign key (group_id)
		references `group`(group_id),
	constraint fk_group_saved_event_saved_event_id
		foreign key (saved_event_id)
		references saved_event(saved_event_id)  
);

create table contact_saved_event (
	contact_id int not null,
    saved_event_id int not null,
    is_attending bit not null default 0,
	constraint pk_contact_saved_event
        primary key (contact_id, saved_event_id),
    constraint fk_contact_saved_event_group_id
		foreign key (contact_id)
		references contact(contact_id),
	constraint fk_contact_saved_event_saved_event_id
		foreign key (saved_event_id)
		references saved_event(saved_event_id)  
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