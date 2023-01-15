package ru.otus.projs.hw17.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.projs.hw17.model.Genre;

@RepositoryRestResource(collectionResourceRel = "genre", path = "genre")
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {}
