package ru.otus.projs.hw08.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.projs.hw08.model.Book;
import ru.otus.projs.hw08.model.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableMongock
@DataMongoTest
@DisplayName("Репозиторий по работе с Comment")
class CommentRepositoryTest {

    private static final String WANTED_COMMENT_ID = "1";
    private static final String WANTED_COMMENT_ID2 = "2";
    private static final String BOOK_ID_4_WANTED_COMMENTS= "2";
    private static final int WANTED_COMMENT_BY_BOOK_ID_COUNT = 2;

    @Autowired
    private BookCommentRepository commentRepo;
    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    void findAllByBookId() {

        List<BookComment> bookComments = commentRepo.getByBookId(BOOK_ID_4_WANTED_COMMENTS);
        assertThat(bookComments).hasSize(WANTED_COMMENT_BY_BOOK_ID_COUNT)
                .allMatch(b -> StringUtils.isNotBlank(b.getComment()));

    }

    @Test
    void getById_FindCommentById() {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(WANTED_COMMENT_ID2));
        var comments = mongoTemplate.find(query, BookComment.class);
        var commentFromRepo = commentRepo.findById(WANTED_COMMENT_ID2);
        assertThat(commentFromRepo.get().getId()).isEqualTo(comments.get(0).getId());

    }

    @Test
    void save_newComment() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(WANTED_COMMENT_ID));
        var book = mongoTemplate.find(query, Book.class);
        var comment = new BookComment(null, "Непонятно", book.get(0));
        var saveComment = commentRepo.save(comment);
        assertThat(comment.getId()).isNotNull();
        var searchedComment = commentRepo.findById(saveComment.getId());

        assertThat(searchedComment).isPresent()
                .matches(b -> b.get().getComment().equals(comment.getComment()))
                .matches(b -> b.get().getBook() != null);
    }

    @Test
    void deleteComment() {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(WANTED_COMMENT_ID));
        var comment2Del = mongoTemplate.find(query, BookComment.class);
        assertThat(comment2Del).hasSize(1);
        commentRepo.deleteById(WANTED_COMMENT_ID);
        assertThat(mongoTemplate.find(query, BookComment.class)).hasSize(0);

    }
}