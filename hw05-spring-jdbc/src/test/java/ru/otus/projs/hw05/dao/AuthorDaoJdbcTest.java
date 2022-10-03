package ru.otus.projs.hw05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw05.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao по работе с Author")
@JdbcTest
@Import({AuthorDaoJdbc.class,BookDaoJdbc.class, GenreDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDao;
    @Autowired
    private BookDaoJdbc bookDao;

    private static final int EXPECTED_ALL_AUTHOR_COUNT = 3;
    private static final String EXPECTED_FIND_BY_ID_AUTHOR_LASTNAME = "Булгаков";

    @Test
    void findAll_find3Authors() {
        List<Author> authors = authorDao.findAll();
        assertThat(authors.size()).isEqualTo(EXPECTED_ALL_AUTHOR_COUNT);
    }

    @Test
    void getById_Bulgakov() {
        Author author = authorDao.getById(1l);
        assertThat(author.getLastName()).isEqualTo(EXPECTED_FIND_BY_ID_AUTHOR_LASTNAME);
    }

    @Test
    void save_newAuthorSaved() {

        Author author = new Author(null, "Иван", "Иванов");
        assertThat(authorDao.findAll().stream().noneMatch(b -> b.getName().equals("Иван") && b.getLastName().equals("Иванов"))).isTrue();
        authorDao.save(author);
        assertThat(authorDao.findAll().stream().anyMatch(b -> b.getName().equals("Иван") && b.getLastName().equals("Иванов"))).isTrue();

    }

    @Test
    void delete_withId_1_andUnLinkingBooks() {

        Author author = authorDao.getById(1l);
        long bookCount = bookDao.getByAuthorId(1l).size();
        assertThat(bookCount).isGreaterThan(0);
        assertThat(author).isNotNull();
        authorDao.delete(author.getId());
        assertThatThrownBy(() -> authorDao.getById(1l));
        assertThatThrownBy(() -> bookDao.getByAuthorId(1l));

    }
}