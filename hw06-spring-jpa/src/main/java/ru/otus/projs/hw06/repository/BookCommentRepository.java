package ru.otus.projs.hw06.repository;

import ru.otus.projs.hw06.model.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {

    List<BookComment> findAllByBookId(Long id);
    Optional<BookComment> getById(Long id);
    BookComment save(BookComment bookComment);
    void delete(Long id);

}
