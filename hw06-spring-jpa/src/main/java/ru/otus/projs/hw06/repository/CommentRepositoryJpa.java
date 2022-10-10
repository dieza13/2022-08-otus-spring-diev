package ru.otus.projs.hw06.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw06.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Comment> findAllByBookId(Long id) {
        TypedQuery<Comment> commentsQuery = entityManager
                .createQuery("select c from Book b join b.comments c where b.id = :id", Comment.class);
        commentsQuery.setParameter("id", id);
        return  commentsQuery.getResultList();
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public Comment save(Comment comment) {

        if (comment.getId() == null || comment.getId() <= 0) {
            entityManager.persist(comment);
            return comment;
        } else {
            return entityManager.merge(comment);
        }

    }

    @Override
    public void delete(Long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
