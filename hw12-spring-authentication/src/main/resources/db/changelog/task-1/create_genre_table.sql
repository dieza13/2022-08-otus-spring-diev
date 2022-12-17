create table genre
(
   id long auto_increment primary key,
   name varchar(255) not null,
   unique(name)
);

CREATE INDEX ind_genre_name_id ON genre(name);