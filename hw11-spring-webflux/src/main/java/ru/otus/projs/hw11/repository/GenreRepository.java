package ru.otus.projs.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.projs.hw11.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
