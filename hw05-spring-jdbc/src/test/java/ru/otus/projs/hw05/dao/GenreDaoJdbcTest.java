package ru.otus.projs.hw05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw05.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("Dao по работе с Genre")
@JdbcTest
@Import({AuthorDaoJdbc.class,BookDaoJdbc.class, GenreDaoJdbc.class})
class GenreDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDao;
    @Autowired
    private GenreDaoJdbc genreDao;

    private static final int EXPECTED_ALL_GENRE_COUNT = 3;
    private static final String EXPECTED_FIND_BY_ID_GENRE_NAME = "Повесть";

    @Test
    void findAll_find3Genres() {
        List<Genre> genres = genreDao.findAll();
        assertThat(genres.size()).isEqualTo(EXPECTED_ALL_GENRE_COUNT);
    }

    @Test
    void getById_Povest() {
        Genre genre = genreDao.getById(1l);
        assertThat(genre.getName()).isEqualTo(EXPECTED_FIND_BY_ID_GENRE_NAME);
    }

    @Test
    void save_newGenreSaved() {

        Genre genre = new Genre(null, "Новелла");
        assertThat(genreDao.findAll().stream().anyMatch(b -> b.getName().equals("Новелла"))).isFalse();
        Genre genre1 = genreDao.save(genre);
        List<Genre> genres = genreDao.findAll();
        long count = genreDao.findAll().stream().filter(b -> b.getName().equals("Новелла")).count();
        assertThat(count).isEqualTo(1);

    }

    @Test
    void delete() {

        Genre genre = genreDao.getById(1l);
        long bookCount = bookDao.findAll().stream().filter(b -> b.getGenre().getId() == 1).count();
        assertThat(bookCount).isGreaterThan(0);
        assertThat(genre).isNotNull();
        genreDao.delete(genre.getId());
        assertThatThrownBy(() -> genreDao.getById(1l));
        bookCount = bookDao.findAll().stream().filter(b -> b.getGenre().getId() == 1).count();
        assertThat(bookCount).isEqualTo(0);

    }
}