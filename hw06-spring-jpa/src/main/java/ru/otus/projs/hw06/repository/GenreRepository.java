package ru.otus.projs.hw06.repository;

import ru.otus.projs.hw06.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();
    Optional<Genre> getById(Long id);
    Genre save(Genre genre);
    void delete(Long id);

}
