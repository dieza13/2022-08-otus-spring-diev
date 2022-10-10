package ru.otus.projs.hw06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw06.model.Author;
import ru.otus.projs.hw06.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий по работе с Comment")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final long WANTED_COMMENT_ID = 1;
    private static final long BOOK_ID_4_WANTED_COMMENTS= 1;
    private static final int WANTED_COMMENT_BY_BOOK_ID_COUNT = 2;

    @Autowired
    private CommentRepositoryJpa commentRepo;

    @Autowired
    private TestEntityManager em;

    @Test
    void findAllByBookId() {

        List<Comment> comments = commentRepo.findAllByBookId(BOOK_ID_4_WANTED_COMMENTS);
        assertThat(comments).hasSize(WANTED_COMMENT_BY_BOOK_ID_COUNT)
                .allMatch(b -> StringUtils.isNotBlank(b.getComment()));

    }

    @Test
    void getById_FindCommentById() {

        var comment = em.find(Comment.class, WANTED_COMMENT_ID);
        var commentFromRepo = commentRepo.getById(WANTED_COMMENT_ID);
        assertThat(commentFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(comment);

    }

    @Test
    void save_newAuthor() {

        var comment = new Comment(null, "Непонятно");
        var saveComment = commentRepo.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);
        var searchedAuthor = commentRepo.getById(saveComment.getId());

        assertThat(searchedAuthor).isPresent()
                .matches(b -> b.get().getComment().equals(comment.getComment()));
    }

    @Test
    void deleteAuthor() {

        var comment2Del = em.find(Author.class,WANTED_COMMENT_ID);
        assertThat(comment2Del).isNotNull();
        em.detach(comment2Del);
        commentRepo.delete(WANTED_COMMENT_ID);
        var commentAfterDel = em.find(Comment.class,WANTED_COMMENT_ID);
        assertThat(commentAfterDel).isNull();

    }
}