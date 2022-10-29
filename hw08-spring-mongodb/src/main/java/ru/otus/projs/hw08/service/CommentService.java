package ru.otus.projs.hw08.service;

import ru.otus.projs.hw08.model.BookComment;

import java.util.List;

public interface CommentService {

    List<BookComment> findAllByBookId(String id);
    BookComment getCommentById(String id);
    BookComment saveComment(BookComment bookComment);
    void deleteComment(String id);

}
