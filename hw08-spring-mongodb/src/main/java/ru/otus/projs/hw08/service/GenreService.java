package ru.otus.projs.hw08.service;

import ru.otus.projs.hw08.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();
    Genre getGenreById(String id);
    Genre saveGenre(Genre genre);
    void deleteGenre(String id);

}
