package ru.otus.projs.hw05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.model.Book;
import ru.otus.projs.hw05.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao по работе с Book")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDao;

    @Autowired
    private AuthorDao authorDao;

    private static final int EXPECTED_ALL_BOOK_COUNT = 5;
    private static final String EXPECTED_FIND_BY_ID_BOOK_NAME = "Лезвие бритвы";

    @Test
    void findAll_find5Books() {
        List<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(EXPECTED_ALL_BOOK_COUNT);
    }

    @Test
    void getById_lezvieBritviBook() {
        Book book = bookDao.getById(1l);
        assertThat(book.getName()).isEqualTo(EXPECTED_FIND_BY_ID_BOOK_NAME);
    }

    @Test
    void getByAuthor_BulgakovBooks() {
        Author author = authorDao.findAll()
                .stream()
                .filter(a -> a.getLastName().equals("Булгаков"))
                .findFirst()
                .orElse(new Author());
        List<Book> books = bookDao.getByAuthorId(author.getId());
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void save_newBookSaved() {
        Book book = new Book(null, "Test book", new Author(1l,null,null), new Genre(1l,null));
        assertThat(bookDao.findAll().stream().noneMatch(b -> b.getName().equals("Test book"))).isTrue();
        bookDao.save(book);
        assertThat(bookDao.findAll().stream().anyMatch(b -> b.getName().equals("Test book"))).isTrue();
    }

    @Test
    void save_bookUpdated() {
        Book book = bookDao.getById(1l);
        assertThat(
                book.getName().equals("Лезвие бритвы") &&
                        book.getAuthor().getId() == 3 &&
                        book.getGenre().getId() == 2

        ).isTrue();
        book.setGenre(new Genre(1l, null));
        book.setAuthor(new Author(1l, null,null));
        bookDao.save(book);
        assertThat(
                book.getName().equals("Лезвие бритвы") &&
                        book.getAuthor().getId() == 1 &&
                        book.getGenre().getId() == 1

        ).isTrue();
    }

    @Test
    void delete() {
        Book book = bookDao.getById(1l);
        assertThat(book).isNotNull();
        bookDao.delete(book.getId());
        assertThatThrownBy(() -> bookDao.getById(1l));
    }
}