create table author
(
   id bigserial not null primary key,
   name varchar(255) not null,
   lastname varchar(255) not null
);
CREATE INDEX ind_author_name_id ON author(name,lastname);
