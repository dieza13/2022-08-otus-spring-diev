package ru.otus.projs.hw11.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.projs.hw11.model.Author;
import ru.otus.projs.hw11.model.Book;
import ru.otus.projs.hw11.model.Genre;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> getByAuthorId(String id);
    Flux<Book> getByGenreId(String id);
    Mono<Long> countByAuthorId(String id);
    Mono<Long> countByGenreId(String id);
    @Query("{ 'author.id' : :#{#authorId} }")
    @Update("{ '$set' : { 'author' : :#{#author} } }")
    void updateAllByAuthorId(@Param("authorId") String authorId, @Param("author") Author author);
    @Query("{ 'genre.id' : :#{#genreId} }")
    @Update("{ '$set' : { 'genre' : :#{#genre} } }")
    void updateAllByGenreId(@Param("genreId") String genreId, @Param("genre") Genre genre);

}
