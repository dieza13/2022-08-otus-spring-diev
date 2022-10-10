package ru.otus.projs.hw06.repository;

import ru.otus.projs.hw06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();
    Optional<Book> getById(Long id);
    Book save(Book book);
    void delete(Long id);
    List<Book> getByAuthorId(long id);

}
