package ru.otus.projs.hw08.service;


import ru.otus.projs.hw08.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();
    Author getAuthorById(String id);
    Author saveAuthor(Author author);
    void deleteAuthor(String id);

}
