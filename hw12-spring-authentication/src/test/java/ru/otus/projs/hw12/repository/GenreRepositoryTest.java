package ru.otus.projs.hw12.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.projs.hw12.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@WithMockUser(
        username = "admin",
        authorities = "ROLE_ADMIN"
)
@DisplayName("Репозиторий по работе с Genre")
@DataJpaTest
class GenreRepositoryTest {

    private static final int ALL_GENRE_QUERY_COUNT = 1;
    private static final int ALL_GENRE_COUNT = 3;
    private static final long WANTED_GENRE_ID = 1;

    @Autowired
    private GenreRepository genreRepo;
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
        var genreFromRepo = genreRepo.findById(WANTED_GENRE_ID);
        assertThat(genreFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void save_newGenre() {

        Genre genre = new Genre(null, "Пьеса");
        var saveGenre = genreRepo.save(genre);
        assertThat(genre.getId()).isGreaterThan(0);
        var searchedGenre = genreRepo.findById(saveGenre.getId());

        assertThat(searchedGenre).isPresent()
                .matches(b -> b.get().getName().equals(genre.getName()));

    }

    @Test
    void deleteGenre_errExistsLinkedBooks() {

        var genre2Del = em.find(Genre.class, WANTED_GENRE_ID);
        assertThat(genre2Del).isNotNull();
        em.detach(genre2Del);
        genreRepo.deleteById(WANTED_GENRE_ID);
        assertThat(em.find(Genre.class,WANTED_GENRE_ID)).isNull();

    }
}