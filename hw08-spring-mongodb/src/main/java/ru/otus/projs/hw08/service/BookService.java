package ru.otus.projs.hw08.service;

import ru.otus.projs.hw08.dto.BookWithComments;
import ru.otus.projs.hw08.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();
    Book getBookById(String id);
    Book saveBook(Book book);
    void deleteBook(String id);
    List<Book> getBookByAuthor(String id);
    BookWithComments getByIdWithComments(String id);
    List<Book> getBookByGenre(String id);

}
