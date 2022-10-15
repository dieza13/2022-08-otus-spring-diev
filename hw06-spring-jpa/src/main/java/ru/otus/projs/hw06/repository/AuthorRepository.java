package ru.otus.projs.hw06.repository;

import ru.otus.projs.hw06.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();
    Optional<Author> getById(Long id);
    Author save(Author author);
    void delete(Long id);

}
