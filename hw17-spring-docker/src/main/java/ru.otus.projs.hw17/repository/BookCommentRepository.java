package ru.otus.projs.hw17.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.projs.hw17.model.BookComment;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bookComment", path = "bookComment")
public interface BookCommentRepository extends PagingAndSortingRepository<BookComment, Long> {

    @RestResource(path = "book", rel = "bookId")
    List<BookComment> findAllByBookId(@Param("bookId") Long id);

}
