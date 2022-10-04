package ru.otus.projs.hw05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.projs.hw05.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao по работе с Author")
@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDao;

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
    void delete_withId_1_andLinkedBooksException() {

        Author author = authorDao.getById(1l);
        assertThat(author).isNotNull();
        assertThatThrownBy(() -> authorDao.delete(author.getId())).isInstanceOf(DataIntegrityViolationException.class);
        assertThat(authorDao.getById(1l)).isNotNull();

    }

    @Test
    void delete_without_books() {
        Author vasia = new Author(null, "Vasia", "Pupkin");
        Author savedVasia = authorDao.save(vasia);
        assertThat(savedVasia.getId()).isGreaterThan(0);
        authorDao.delete(savedVasia.getId());
        assertThatThrownBy(()->authorDao.getById(savedVasia.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

}