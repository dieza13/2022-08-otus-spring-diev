package ru.otus.projs.hw07.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.projs.hw07.model.Book;
import ru.otus.projs.hw07.model.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий по работе с Comment")
@DataJpaTest
class CommentRepositoryTest {

    private static final long WANTED_COMMENT_ID = 1;
    private static final long BOOK_ID_4_WANTED_COMMENTS= 1;
    private static final int WANTED_COMMENT_BY_BOOK_ID_COUNT = 2;

    @Autowired
    private BookCommentRepository commentRepo;

    @Autowired
    private TestEntityManager em;

    @Test
    void findAllByBookId() {

        List<BookComment> bookComments = commentRepo.findAllByBookId(BOOK_ID_4_WANTED_COMMENTS);
        assertThat(bookComments).hasSize(WANTED_COMMENT_BY_BOOK_ID_COUNT)
                .allMatch(b -> StringUtils.isNotBlank(b.getComment()));

    }

    @Test
    void getById_FindCommentById() {

        var comment = em.find(BookComment.class, WANTED_COMMENT_ID);
        var commentFromRepo = commentRepo.findById(WANTED_COMMENT_ID);
        assertThat(commentFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(comment);

    }

    @Test
    void save_newComment() {
        var book = em.find(Book.class, 1l);
        var comment = new BookComment(null, "Непонятно", book);
        var saveComment = commentRepo.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);
        var searchedComment = commentRepo.findById(saveComment.getId());

        assertThat(searchedComment).isPresent()
                .matches(b -> b.get().getComment().equals(comment.getComment()))
                .matches(b -> b.get().getBook() != null);
    }

    @Test
    void deleteComment() {

        var comment2Del = em.find(BookComment.class,WANTED_COMMENT_ID);
        assertThat(comment2Del).isNotNull();
        em.detach(comment2Del);
        commentRepo.delete(comment2Del);
        var commentAfterDel = em.find(BookComment.class,WANTED_COMMENT_ID);
        assertThat(commentAfterDel).isNull();

    }
}