create table comment
(
   id long auto_increment primary key,
   comment varchar(255) not null,
   book_id long,
   CONSTRAINT fk_comment_book_id FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);
CREATE INDEX ind_comment_book_id ON comment(book_id);
