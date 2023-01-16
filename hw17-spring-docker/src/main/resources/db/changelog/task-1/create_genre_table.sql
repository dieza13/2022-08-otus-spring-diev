create table genre
(
   id bigserial not null primary key,
   name varchar(255) not null,
   unique(name)
);

CREATE INDEX ind_genre_name_id ON genre(name);