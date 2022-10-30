package ru.otus.projs.hw08.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import ru.otus.projs.hw08.model.Author;
import ru.otus.projs.hw08.model.Book;
import ru.otus.projs.hw08.model.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> getByAuthorId(String id);
    List<Book> getByGenreId(String id);
    Long countByAuthorId(String id);
    Long countByGenreId(String id);
    @Query("{ 'author.id' : :#{#authorId} }")
    @Update("{ '$set' : { 'author' : :#{#author} } }")
    void updateAllByAuthorId(@Param("authorId") String authorId, @Param("author") Author author);
    @Query("{ 'genre.id' : :#{#genreId} }")
    @Update("{ '$set' : { 'genre' : :#{#genre} } }")
    void updateAllByGenreId(@Param("genreId") String genreId, @Param("genre") Genre genre);

}
