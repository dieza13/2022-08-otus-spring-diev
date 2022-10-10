package ru.otus.projs.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw06.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;
import static ru.otus.projs.hw06.model.Book.BOOK_AUTHOR_GENRE_GRAPH;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> authorGenreEntityGraph = entityManager.getEntityGraph(BOOK_AUTHOR_GENRE_GRAPH);
        TypedQuery<Book> bookQuery = entityManager.createQuery("select b from Book b", Book.class);
        bookQuery.setHint(FETCH.getKey(), authorGenreEntityGraph);
        return  bookQuery.getResultList();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getByAuthorId(long id) {
        EntityGraph<?> authorGenreEntityGraph = entityManager.getEntityGraph(BOOK_AUTHOR_GENRE_GRAPH);
        TypedQuery<Book> bookQuery = entityManager.createQuery("select b from Book b where b.author.id = :id", Book.class);
        bookQuery.setParameter("id", id);
        bookQuery.setHint(FETCH.getKey(), authorGenreEntityGraph);
        return  bookQuery.getResultList();
    }

    @Override
    public Book save(Book book) {

        if (book.getId() == null || book.getId() <= 0) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }

    }

    @Override
    public void delete(Long id) {
        Query query = entityManager.createQuery("delete from Book a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
