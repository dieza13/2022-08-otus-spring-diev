package ru.otus.projs.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.projs.hw11.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
