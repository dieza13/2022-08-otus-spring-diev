package ru.otus.projs.hw09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.projs.hw09.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
