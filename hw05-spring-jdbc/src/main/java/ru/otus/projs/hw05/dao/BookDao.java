package ru.otus.projs.hw05.dao;

import ru.otus.projs.hw05.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> findAll();
    Book getById(Long id);
    Book save(Book book);
    void delete(Long id);
    List<Book> getByAuthorId(long id);

}
