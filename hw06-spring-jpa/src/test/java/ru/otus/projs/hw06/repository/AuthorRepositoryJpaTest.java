package ru.otus.projs.hw06.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw06.model.Author;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Репозиторий по работе с Author")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {


    private static final int ALL_AUTHOR_QUERY_COUNT = 1;
    private static final int ALL_AUTHOR_COUNT = 3;
    private static final long WANTED_AUTHOR_ID = 1;

    @Autowired
    private AuthorRepositoryJpa authorRepo;
    @Autowired
    private TestEntityManager em;

    @Test
    void findAll_AuthorsInfoIn2Queries() {

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var authors = authorRepo.findAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(ALL_AUTHOR_QUERY_COUNT);
        assertThat(authors).isNotNull().hasSize(ALL_AUTHOR_COUNT)
                .allMatch(b -> !b.getName().equals(""));

    }

    @Test
    void getById_FindAuthorById() {

        var author = em.find(Author.class, WANTED_AUTHOR_ID);
        var authorFromRepo = authorRepo.getById(WANTED_AUTHOR_ID);
        assertThat(authorFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void save_newAuthor() {

        Author author = new Author(null, "Михаил", "Лермонтов");
        var saveAuthor = authorRepo.save(author);
        assertThat(author.getId()).isGreaterThan(0);
        var searchedAuthor = authorRepo.getById(saveAuthor.getId());

        assertThat(searchedAuthor.get()).isNotNull()
                .matches(b -> b.getName().equals(author.getName()))
                .matches(b -> b.getLastName().equals(author.getLastName()));

    }

    @Test
    void deleteAuthor_errExistsLinkedBooks() {

        var author2Del = em.find(Author.class,WANTED_AUTHOR_ID);
        assertThat(author2Del).isNotNull();
        assertThatThrownBy(() -> authorRepo.delete(WANTED_AUTHOR_ID)).isInstanceOf(PersistenceException.class);

    }
}