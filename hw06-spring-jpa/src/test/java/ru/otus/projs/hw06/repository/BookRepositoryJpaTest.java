package ru.otus.projs.hw06.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw06.dto.BookWithComments;
import ru.otus.projs.hw06.model.Author;
import ru.otus.projs.hw06.model.Book;
import ru.otus.projs.hw06.model.BookComment;
import ru.otus.projs.hw06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Репозиторий по работе с Book")
@DataJpaTest
@Import({BookRepositoryJpa.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepo;
    @MockBean
    private BookCommentRepository bookCommentRepo;
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
        var bookFromRepo = bookRepo.getById(WANTED_BOOK_ID);
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
    void getByIdWithComments() {
        BookComment comment1 = new BookComment(1l, "com1" , null);
        BookComment comment2 = new BookComment(2l, "com2" , null);
        List<BookComment> comments = List.of(comment1, comment2);
        when(bookCommentRepo.findAllByBookId(WANTED_BOOK_ID)).thenReturn(comments);

        BookWithComments book = bookRepo.getByIdWithComments(WANTED_BOOK_ID);

        assertThat(book).isNotNull()
                .matches(b -> b.getBook() != null)
                .matches(b -> b.getComments().equals(comments));

        assertThat(book.getBook())
                .matches(b -> b.getGenre() != null)
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getId() == 1);

    }

    @Test
    void save_fullSetBook() {

        Genre genre = new Genre(null, "Стих");
        Author author = new Author(null, "Михаил", "Лермонтов");
        Book book = new Book(null, "Мцыри", author, genre);

        var savedBook = bookRepo.save(book);
        assertThat(book.getId()).isGreaterThan(0);
        var searchedBook = bookRepo.getById( savedBook.getId());

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
        bookRepo.delete(WANTED_BOOK_ID);
        var bookAfterDel = em.find(Book.class,WANTED_BOOK_ID);
        assertThat(bookAfterDel).isNull();

    }
}