package ru.otus.projs.hw12.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw12.model.Author;
import ru.otus.projs.hw12.model.Book;
import ru.otus.projs.hw12.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий по работе с Book")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private TestEntityManager em;

    private static final int ALL_BOOK_QUERY_COUNT = 1;
    private static final int ALL_BOOK_COUNT = 5;
    private static final long WANTED_BOOK_ID = 1;
    private static final long AUTHOR_ID_4_WANTED_BOOKS = 1;
    private static final int WANTED_BOOKS_BY_AUTHOR_ID_COUNT = 3;
    private static final List<String> WANTED_BOOKS_NAME = List.of("Морфий", "Собачье сердце", "Роковые яйца");


    @Test
    void findAll_AllInfoBooksIn2Queries() {

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var books = bookRepo.findAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(ALL_BOOK_QUERY_COUNT);
        assertThat(books).isNotNull().hasSize(ALL_BOOK_COUNT)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthor() != null);

    }

    @Test
    void getById_FindBookById() {

        var book = em.find(Book.class, WANTED_BOOK_ID);
        var bookFromRepo = bookRepo.findById(WANTED_BOOK_ID);
        assertThat(bookFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(book);

    }

    @Test
    void getByAuthorId() {

        List<Book> books = bookRepo.getByAuthorId(AUTHOR_ID_4_WANTED_BOOKS);
        assertThat(books).hasSize(WANTED_BOOKS_BY_AUTHOR_ID_COUNT)
                .allMatch(b -> WANTED_BOOKS_NAME.stream()
                        .anyMatch(bn -> b.getName().equals(bn)));

    }



    @Test
    void save_fullSetBook() {

        Genre genre = new Genre(1L, "Стих");
        Author author = new Author(2L, "Михаил", "Лермонтов");
        Book book = new Book(null, "Мцыри", author, genre);

        var savedBook = bookRepo.save(book);
        assertThat(book.getId()).isGreaterThan(0);
        var searchedBook = bookRepo.findById( savedBook.getId());

        assertThat(searchedBook.get()).isNotNull()
                .matches(b -> b.getName().equals(book.getName()))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);

    }

    @Test
    void delete_book() {

        var book2Del = em.find(Book.class,WANTED_BOOK_ID);
        assertThat(book2Del).isNotNull();
        em.detach(book2Del);
        bookRepo.deleteById(WANTED_BOOK_ID);
        var bookAfterDel = em.find(Book.class,WANTED_BOOK_ID);
        assertThat(bookAfterDel).isNull();

    }
}