drop database if exists lets_go_test;
create database lets_go_test;

use lets_go_test;

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
    country varchar(100) not null,
    zipcode int null
);

create table `event` (
	event_id int primary key auto_increment,
	event_name varchar(100) not null,
    category varchar(50) not null,
    image_url varchar(250) not null,
    `description` text,
    event_date datetime not null,
    `source` varchar(50) null,
    source_id varchar(100) null,
    event_link varchar(250) null,
    venue_id int not null,
    constraint fk_event_venue_id
		foreign key (venue_id)
		references venue(venue_id)
);

create table saved_event (
	event_id int not null,
    app_user_id int not null,
	constraint pk_saved_event
        primary key (app_user_id, event_id),
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

delimiter //
create procedure set_known_good_state()
begin
	delete from saved_event;
	delete from group_contact;
    delete from contact;
    alter table contact auto_increment = 1;
    delete from `group`;
    alter table `group` auto_increment = 1;
	delete from event_post;
    alter table event_post auto_increment = 1;
    delete from `event`;
    alter table `event` auto_increment = 1;
    delete from venue;
    alter table venue auto_increment=1;
    delete from app_user_role;
    delete from app_role;
    alter table app_role auto_increment=1;
    delete from app_user;
    alter table app_user auto_increment=1;
   
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
    
    insert into venue(venue_id, venue_name, address, city, state, country, zipcode) 
		values 
        (1, 'The Wine Cellar', '2222 Fifth St.', 'New York', 'NY', 'United States', 29292),
        (2, 'House of Blues', '2222 42nd St.', 'Chicago', 'IL', 'United States', 25552),
		(3, 'NRG Stadium', '1922 Fondren St.', 'Houston', 'TX', 'United States', 77282);
        
	
    insert into `event`(event_id,  category, event_name, image_url, `description`, event_date, `source`, source_id, event_link, venue_id)
    values
        (1, 'concert', 'Young The Giant with Grouplove', 'https://chairnerd.global.ssl.fastly.net/images/performers/8741/555bce1815140ad65ab0b1066467ae7d/huge.jpg',
        "", '2012-03-09T19:00:00', 'SeatGeek', '721901', "https://example.com", 1),
		(2, 'sports', 'Knicks vs Spurs', 'www.coolpic.net/image/huge.jpg',  '', '2015-08-09T19:00:00', 'SeatGeek', '728901',"https://example.com", 2),
		(3, 'sports', 'Bulls vs Spurs', 'www.coolpic4.net/image/large.jpg',  '', '2018-08-09T19:00:00', 'TicketMaster', '1034901',"https://example.com", 3);

insert into event_post (event_post_id, event_id, app_user_id, post_body, likes)
values 
(1, 1, 1, 'Who is going to this concert?', 2),
(2, 1, 2, "I'm going.", 5),
(3, 2, 1, "Me too.", 3);

insert into saved_event(event_id, app_user_id)
values
(1,2),
(2,3),
(3,1);


insert into contact(app_user_id, email, phone, first_name, last_name)
values 
(1, 'blue@gmail.com', 2222222, 'Rick', 'James'),
(1, 'red@gmail.com', 2222222, 'Martha', 'Stewart'),
(2, 'yellow@gmail.com', 2222222, 'Natalie', 'Portman');


insert into `group`(app_user_id, group_name)
values 
(2, 'The Adventurers'),
(2, 'Three Amigos'),
(3, 'Yahoos');

insert into group_contact(group_id, contact_id)
values
(2, 1),
(3, 2),
(1, 3);

end //
-- 4. Change the statement terminator back to the original.
delimiter ;