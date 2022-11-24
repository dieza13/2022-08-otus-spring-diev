package ru.otus.projs.hw10.service;

import ru.otus.projs.hw10.model.BookComment;

import java.util.List;

public interface CommentService {

    List<BookComment> findAllByBookId(Long id);
    BookComment getCommentById(Long id);
    BookComment saveComment(BookComment bookComment);
    void deleteComment(long id);

}
