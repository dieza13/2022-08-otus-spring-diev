package ru.otus.projs.hw06.service;

import ru.otus.projs.hw06.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllByBookId(Long id);
    Comment getCommentById(Long id);
    Comment saveComment(Comment comment);
    void deleteComment(long id);

}
