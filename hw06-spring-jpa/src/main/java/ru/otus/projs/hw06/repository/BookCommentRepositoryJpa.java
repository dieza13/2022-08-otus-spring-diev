package ru.otus.projs.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw06.model.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<BookComment> findAllByBookId(Long id) {
        TypedQuery<BookComment> commentsQuery = entityManager
                .createQuery("select c from BookComment c join c.book b where b.id = :id", BookComment.class);
        commentsQuery.setParameter("id", id);
        return  commentsQuery.getResultList();
    }

    @Override
    public Optional<BookComment> getById(Long id) {
        return Optional.ofNullable(entityManager.find(BookComment.class, id));
    }

    @Override
    public BookComment save(BookComment bookComment) {

        if (bookComment.getId() == null || bookComment.getId() <= 0) {
            entityManager.persist(bookComment);
            return bookComment;
        } else {
            return entityManager.merge(bookComment);
        }

    }

    @Override
    public void delete(Long id) {
        getById(id).ifPresent(entityManager::remove);
    }

}
