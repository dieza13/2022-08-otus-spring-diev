package ru.otus.projs.hw08.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.projs.hw08.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {}
