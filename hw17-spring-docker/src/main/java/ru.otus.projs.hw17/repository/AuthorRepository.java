package ru.otus.projs.hw17.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.projs.hw17.model.Author;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "author", path = "author")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    @RestResource(path = "name", rel = "lastname")
    List<Author> findByLastName(String lastname);
}
