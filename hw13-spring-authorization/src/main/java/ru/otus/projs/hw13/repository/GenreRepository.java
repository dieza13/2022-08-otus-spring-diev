package ru.otus.projs.hw13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.projs.hw13.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
