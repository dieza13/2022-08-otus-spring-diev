package ru.otus.projs.hw08.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.projs.hw08.model.Author;
import ru.otus.projs.hw08.model.Book;
import ru.otus.projs.hw08.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableMongock
@DataMongoTest
@DisplayName("Репозиторий по работе с Book")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;
    @MockBean
    private BookCommentRepository bookCommentRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String WANTED_BOOK_ID = "1";
    private static final String WANTED_BOOK_ID_4_DEL = "1";
    private static final String AUTHOR_ID_4_WANTED_BOOKS = "1";
    private static final String GENRE_ID_4_WANTED_BOOKS = "1";
    private static final int WANTED_BOOKS_BY_AUTHOR_ID_COUNT = 3;
    private static final int WANTED_BOOKS_BY_GENRE_ID_COUNT = 3;
    private static final List<String> WANTED_BOOKS_NAME1 = List.of("22222", "44444", "55555");
    private static final List<String> WANTED_BOOKS_NAME2 = List.of("22222", "33333", "55555");



    @Test
    void find_BookById() {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(WANTED_BOOK_ID));
        var book = mongoTemplate.find(query, Book.class);
        var bookFromRepo = bookRepo.findById(WANTED_BOOK_ID);
        assertThat(bookFromRepo.get().getId()).isEqualTo(book.get(0).getId());

    }

    @Test
    void get_ByAuthorId() {

        List<Book> books = bookRepo.getByAuthorId(AUTHOR_ID_4_WANTED_BOOKS);
        assertThat(books).hasSize(WANTED_BOOKS_BY_AUTHOR_ID_COUNT)
                .allMatch(b -> WANTED_BOOKS_NAME1.stream()
                        .anyMatch(bn -> b.getName().equals(bn)));

    }

    @Test
    void get_ByGenreId() {

        List<Book> books = bookRepo.getByGenreId(GENRE_ID_4_WANTED_BOOKS);
        assertThat(books).hasSize(WANTED_BOOKS_BY_GENRE_ID_COUNT)
                .allMatch(b -> WANTED_BOOKS_NAME2.stream()
                        .anyMatch(bn -> b.getName().equals(bn)));

    }

    @Test
    void count_ByAuthorId() {

        long count = bookRepo.countByAuthorId(AUTHOR_ID_4_WANTED_BOOKS);
        assertThat(count).isEqualTo(WANTED_BOOKS_BY_AUTHOR_ID_COUNT);

    }

    @Test
    void count_ByGenreId() {

        long count = bookRepo.countByGenreId(GENRE_ID_4_WANTED_BOOKS);
        assertThat(count).isEqualTo(WANTED_BOOKS_BY_GENRE_ID_COUNT);

    }


    @Test
    void save_fullSetBook() {

        var savedBook = bookRepo.save(getFullBook("7"));
        assertThat(savedBook.getId()).isNotNull();
        var searchedBook = bookRepo.findById( savedBook.getId());

        Assertions.assertThat(searchedBook.get()).isNotNull()
                .matches(b -> b.getName().equals(savedBook.getName()))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);

    }

    @Test
    void delete_book() {

        Book book2Save = bookRepo.save(getFullBook("8"));
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(book2Save.getId()));
        var book2Del = mongoTemplate.find(query, Book.class);
        assertThat(book2Del).hasSize(1);
        bookRepo.deleteById(book2Del.get(0).getId());
        assertThat(mongoTemplate.find(query, Book.class)).hasSize(0);

    }

    private Book getFullBook(String id) {
        Genre genre = new Genre(id, id.repeat(4));
        Author author = new Author(id, id.repeat(3), id.repeat(3));
        return new Book(id, id.repeat(2), author, genre);
    }

}