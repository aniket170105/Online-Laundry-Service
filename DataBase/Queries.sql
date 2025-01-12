drop database laundry;

create database laundry;

use laundry;

create table user(
	username varchar(255),
    user_id varchar(255) primary key,
    email varchar(255),
    password varchar(255),
    laundry_id varchar(255),
    hostel varchar(10),
    is_admin boolean
);

create table laundry(
	id int primary key auto_increment,
    user_id varchar(255),
    status ENUM('PICKED', 'WASHING', 'DONE', 'DELEVIERED'),
    date datetime default current_timestamp,
    foreign key (user_id) references user(user_id)
);

create table message(
	id int primary key auto_increment,
    laundry_id int,
    message text,
    foreign key (laundry_id) references laundry(id)
);

create table pants(
	id int primary key auto_increment,
    laundry_id int,
    image longblob,
    foreign key (laundry_id) references laundry(id)
);

create table shirts(
	id int primary key auto_increment,
    laundry_id int,
    image longblob,
    foreign key (laundry_id) references laundry(id)
);



select * from session_token;







