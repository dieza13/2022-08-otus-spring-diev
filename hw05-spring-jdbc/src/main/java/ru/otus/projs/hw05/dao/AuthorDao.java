package ru.otus.projs.hw05.dao;

import ru.otus.projs.hw05.model.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> findAll();
    Author getById(Long id);
    Author save(Author author);
    void delete(Long id);

}
