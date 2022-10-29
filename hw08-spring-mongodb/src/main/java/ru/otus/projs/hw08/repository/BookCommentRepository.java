package ru.otus.projs.hw08.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.projs.hw08.model.BookComment;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {

    List<BookComment> getByBookId(String id);

}
