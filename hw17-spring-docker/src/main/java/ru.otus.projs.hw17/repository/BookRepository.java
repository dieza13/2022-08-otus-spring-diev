package ru.otus.projs.hw17.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.projs.hw17.model.Book;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @RestResource(path = "author", rel = "authorId")
    List<Book> getByAuthorId(long authorId);

}
