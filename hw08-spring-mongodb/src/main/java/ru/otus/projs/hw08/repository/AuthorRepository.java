package ru.otus.projs.hw08.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.projs.hw08.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {}
