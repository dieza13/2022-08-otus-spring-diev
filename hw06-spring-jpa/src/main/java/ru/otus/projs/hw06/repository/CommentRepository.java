package ru.otus.projs.hw06.repository;

import ru.otus.projs.hw06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAllByBookId(Long id);
    Optional<Comment> getById(Long id);
    Comment save(Comment comment);
    void delete(Long id);

}
