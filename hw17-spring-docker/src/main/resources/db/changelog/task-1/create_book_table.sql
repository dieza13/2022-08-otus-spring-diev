create table book
(
   id bigserial not null primary key,
   name varchar(255) not null,
   author_id bigint not null,
   genre_id bigint not null,

   CONSTRAINT fk_book_genre_id FOREIGN KEY (genre_id) REFERENCES genre(id) ON UPDATE CASCADE,
   CONSTRAINT fk_book_author_id FOREIGN KEY (author_id) REFERENCES author(id) ON UPDATE CASCADE
);

CREATE INDEX ind_book_genre_id ON book(genre_id);
CREATE INDEX ind_book_author_id ON book(author_id);
CREATE INDEX ind_book_name ON book(name);