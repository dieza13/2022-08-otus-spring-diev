package ru.otus.projs.hw12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.projs.hw12.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
