package ru.otus.projs.hw10.service;

import ru.otus.projs.hw10.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();
    Genre getGenreById(Long id);
    Genre saveGenre(Genre genre);
    void deleteGenre(long id);

}
