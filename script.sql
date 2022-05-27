create
database HotelManagement;

use
HotelManagement;

create table if not exists Hotel
(
    id            int auto_increment
    primary key,
    address       varchar(255) charset utf8 null,
    quantity_room int                       null
    );

create table if not exists User
(
    id        int auto_increment
    primary key,
    username  varchar(255)              null,
    password  varchar(255)              null,
    full_name varchar(255) charset utf8 null,
    address   varchar(255) charset utf8 null,
    phone     varchar(20)               null,
    role      int                       null comment '0: owner; 1: guest',
    constraint username
    unique (username)
    );

create table if not exists ap_param
(
    id    int auto_increment
    primary key,
    value varchar(255) charset utf8 null,
    type  varchar(255)              null,
    code  varchar(255)              null,
    constraint ap_param_code_uindex
    unique (code)
    );

create table if not exists type_room
(
    id   int auto_increment
    primary key,
    name varchar(255) charset utf8 null
    );

create table if not exists Room
(
    id           int auto_increment
    primary key,
    people       varchar(255)              null,
    type_room_id int                       null,
    price        float                     null,
    description  varchar(255) charset utf8 null,
    hotel_id     int                       null,
    image        text                      null,
    constraint Room_Hotel_id_fk
    foreign key (hotel_id) references Hotel (id)
    on delete cascade,
    constraint Room_ap_param_code_fk
    foreign key (people) references ap_param (code)
    on delete cascade,
    constraint Room_type_room_id_fk
    foreign key (type_room_id) references type_room (id)
    on delete cascade
    );

create table if not exists Booking
(
    id        int auto_increment
    primary key,
    book_date timestamp    null,
    username  varchar(200) null,
    room_id   int          null,
    price     float        null,
    from_date date         null,
    to_date   date         null,
    constraint Booking_Room_id_fk
    foreign key (room_id) references Room (id)
    on delete cascade,
    constraint Booking_User_username_fk
    foreign key (username) references User (username)
    on delete cascade
    );
