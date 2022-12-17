package ru.otus.projs.hw12.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.projs.hw12.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий по работе с Author")
@DataJpaTest
class AuthorRepositoryTest {


    private static final int ALL_AUTHOR_QUERY_COUNT = 1;
    private static final int ALL_AUTHOR_COUNT = 3;
    private static final long WANTED_AUTHOR_ID = 1;

    @Autowired
    private AuthorRepository authorRepo;
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
        var authorFromRepo = authorRepo.findById(WANTED_AUTHOR_ID);
        assertThat(authorFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void save_newAuthor() {

        Author author = new Author(null, "Михаил", "Лермонтов");
        var saveAuthor = authorRepo.save(author);
        assertThat(author.getId()).isGreaterThan(0);
        var searchedAuthor = authorRepo.findById(saveAuthor.getId());

        assertThat(searchedAuthor.get()).isNotNull()
                .matches(b -> b.getName().equals(author.getName()))
                .matches(b -> b.getLastName().equals(author.getLastName()));

    }

    @Test
    void deleteAuthor_errExistsLinkedBooks() {

        var author2Del = em.find(Author.class,WANTED_AUTHOR_ID);
        assertThat(author2Del).isNotNull();
        em.detach(author2Del);
        authorRepo.deleteById(WANTED_AUTHOR_ID);
        assertThat(em.find(Author.class,WANTED_AUTHOR_ID)).isNull();


    }
}