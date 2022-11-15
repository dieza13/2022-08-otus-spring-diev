package ru.otus.projs.hw09.service;

import ru.otus.projs.hw09.dto.BookWithComments;
import ru.otus.projs.hw09.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(long id);
    List<Book> getBookByAuthor(long id);
    BookWithComments getByIdWithComments(Long id);

}
