package ru.otus.projs.hw07.service;

import ru.otus.projs.hw07.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();
    Author getAuthorById(Long id);
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);

}
