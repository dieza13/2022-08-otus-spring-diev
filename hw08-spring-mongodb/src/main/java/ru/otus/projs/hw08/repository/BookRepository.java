package ru.otus.projs.hw08.repository;


import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.projs.hw08.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> getByAuthorId(String id);
    List<Book> getByGenreId(String id);
    Long countByAuthorId(String id);
    Long countByGenreId(String id);

}
