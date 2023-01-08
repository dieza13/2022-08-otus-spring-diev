package ru.otus.projs.hw16.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.projs.hw16.model.Genre;

@RepositoryRestResource(collectionResourceRel = "genre", path = "genre")
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {}
