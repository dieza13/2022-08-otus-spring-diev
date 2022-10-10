package ru.otus.projs.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw06.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> genresQuery = entityManager.createQuery("select g from Genre g", Genre.class);
        return  genresQuery.getResultList();
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Genre save(Genre genre) {

        if (genre.getId() == null || genre.getId() <= 0) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }

    }

    @Override
    public void delete(Long id) {
        Query query = entityManager.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
