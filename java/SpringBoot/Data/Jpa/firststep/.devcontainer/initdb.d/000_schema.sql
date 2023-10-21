drop table if exists Attendance;

drop table if exists Account;
drop table if exists Role;

drop table if exists Course;
drop table if exists Classroom;
drop table if exists ZoomChannel;

create table Role (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null unique
);

create table Account (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null unique,
  role bigint not null,
  foreign key (role) references Role (id)
);

create table Classroom (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null unique
);

create table ZoomChannel (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null unique
);

create table Course (
  id bigint primary key AUTO_INCREMENT,
  name varchar(255) not null unique,
  course_type varchar(255) not null,
  classroom bigint,
  zoom_channel bigint,
  foreign key (classroom) references Classroom (id),
  foreign key (zoom_channel) references ZoomChannel (id)
);

create table Attendance (
  id bigint primary key AUTO_INCREMENT,
  account bigint not null,
  course bigint not null,
  foreign key (account) references Account (id),
  foreign key (course) references Course (id)
);
