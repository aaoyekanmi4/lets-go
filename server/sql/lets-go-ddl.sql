use lets_go;

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

