package ru.otus.projs.hw06.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw06.model.Genre;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Репозиторий по работе с Genre")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final int ALL_GENRE_QUERY_COUNT = 1;
    private static final int ALL_GENRE_COUNT = 3;
    private static final long WANTED_GENRE_ID = 1;

    @Autowired
    private GenreRepositoryJpa genreRepo;
    @Autowired
    private TestEntityManager em;

    @Test
    void findAll_GenresInfoIn2Queries() {

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var genres = genreRepo.findAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(ALL_GENRE_QUERY_COUNT);
        assertThat(genres).isNotNull().hasSize(ALL_GENRE_COUNT)
                .allMatch(b -> !b.getName().equals(""));

    }

    @Test
    void getById_FindGenreById() {

        var genre = em.find(Genre.class, WANTED_GENRE_ID);
        var genreFromRepo = genreRepo.getById(WANTED_GENRE_ID);
        assertThat(genreFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void save_newGenre() {

        Genre genre = new Genre(null, "Пьеса");
        var saveGenre = genreRepo.save(genre);
        assertThat(genre.getId()).isGreaterThan(0);
        var searchedGenre = genreRepo.getById(saveGenre.getId());

        assertThat(searchedGenre).isPresent()
                .matches(b -> b.get().getName().equals(genre.getName()));

    }

    @Test
    void deleteGenre_errExistsLinkedBooks() {

        var genre2Del = em.find(Genre.class, WANTED_GENRE_ID);
        assertThat(genre2Del).isNotNull();
        assertThatThrownBy(() -> genreRepo.delete(WANTED_GENRE_ID)).isInstanceOf(PersistenceException.class);

    }
}