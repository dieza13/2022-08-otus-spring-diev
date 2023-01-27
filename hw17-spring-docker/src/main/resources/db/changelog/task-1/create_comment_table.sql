create table book_comment
(
   id bigserial not null primary key,
   comment varchar(255) not null,
   book_id bigint not null,
   CONSTRAINT fk_comment_book_id FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);
CREATE INDEX ind_comment_book_id ON book_comment(book_id);
