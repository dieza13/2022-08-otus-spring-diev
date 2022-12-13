create table author
(
   id long auto_increment primary key,
   name varchar(255) not null,
   lastname varchar(255) not null
);
CREATE INDEX ind_author_name_id ON author(name,lastname);
