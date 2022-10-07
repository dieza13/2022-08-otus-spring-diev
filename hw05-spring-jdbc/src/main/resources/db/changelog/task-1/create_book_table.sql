create table book
(
   id long auto_increment primary key,
   name varchar(255) not null,
   author_id long not null,
   genre_id long not null,

   CONSTRAINT fk_book_genre_id FOREIGN KEY (genre_id) REFERENCES genre(id) ON UPDATE CASCADE,
   CONSTRAINT fk_book_author_id FOREIGN KEY (author_id) REFERENCES author(id) ON UPDATE CASCADE
);

CREATE INDEX ind_book_genre_id ON book(genre_id);
CREATE INDEX ind_book_author_id ON book(author_id);
CREATE INDEX ind_book_name ON book(name);