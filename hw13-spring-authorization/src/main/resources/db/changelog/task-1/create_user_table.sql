create table usr
(
   id long auto_increment primary key,
   username varchar(255) not null,
   password varchar(255) not null,
   name varchar(255) not null
);
CREATE INDEX ind_user_username ON usr(username);
