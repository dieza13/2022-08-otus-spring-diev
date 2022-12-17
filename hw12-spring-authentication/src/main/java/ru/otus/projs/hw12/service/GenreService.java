package ru.otus.projs.hw12.service;

import ru.otus.projs.hw12.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();
    Genre getGenreById(Long id);
    Genre saveGenre(Genre genre);
    void deleteGenre(long id);

}
