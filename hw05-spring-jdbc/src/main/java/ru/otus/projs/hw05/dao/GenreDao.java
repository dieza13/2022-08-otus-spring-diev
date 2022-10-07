package ru.otus.projs.hw05.dao;

import ru.otus.projs.hw05.model.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> findAll();
    Genre getById(Long id);
    Genre save(Genre genre);
    void delete(Long id);

}
