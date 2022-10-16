package ru.otus.projs.hw07.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.projs.hw07.model.BookComment;

import java.util.List;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    @EntityGraph(attributePaths = { "book" }, type = EntityGraph.EntityGraphType.LOAD)
    List<BookComment> findAllByBookId(Long id);

}
