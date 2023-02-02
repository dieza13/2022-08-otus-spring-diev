package ru.otus.projs.hw18.service;

import ru.otus.projs.hw18.model.Book;

import java.util.List;

public interface BookServiceWithHystrix {

    List<Book> findAll();
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(long id);
    List<Book> getBookByAuthor(long id);

}
