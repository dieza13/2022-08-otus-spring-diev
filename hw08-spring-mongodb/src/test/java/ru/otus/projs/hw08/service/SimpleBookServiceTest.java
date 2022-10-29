package ru.otus.projs.hw08.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw08.dto.BookWithComments;
import ru.otus.projs.hw08.exception.*;
import ru.otus.projs.hw08.model.Author;
import ru.otus.projs.hw08.model.Book;
import ru.otus.projs.hw08.model.BookComment;
import ru.otus.projs.hw08.model.Genre;
import ru.otus.projs.hw08.repository.BookCommentRepository;
import ru.otus.projs.hw08.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SimpleBookService.class})
@DisplayName("Сервис по работе с Book")
class SimpleBookServiceTest {

    @Autowired
    private SimpleBookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private BookCommentRepository  commentRepository;

    private static final String WANTED_BOOK_ID = "1";

    @Test
    void findAll_ok() {
        Book book1 = createBook("1");
        Book book2 = createBook("2");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> books = bookService.findAll();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books).contains(book1,book2);
    }

    @Test
    void findAll_Exception() {
        when(bookRepository.findAll()).thenThrow(FindAllBookException.class);
        assertThatThrownBy(() -> bookService.findAll()).isInstanceOf(FindAllBookException.class);
    }

    @Test
    void getBookById_1() {
        Book book1 = createBook("1");
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        Book book = bookService.getBookById("1");
        assertThat(book.getId()).isEqualTo(book1.getId());
    }

    @Test
    void getBookById_Exception() {
        when(bookRepository.findById("1")).thenThrow(GetBookByIdException.class);
        assertThatThrownBy(() -> bookService.getBookById("1")).isInstanceOf(GetBookByIdException.class);
    }

    @Test
    void saveBook_withIdAndName() {
        Book book1 = createBook("1");
        when(bookRepository.save(book1)).thenReturn(book1);
        Book book = bookService.saveBook(book1);
        assertThat(book.getName()).isEqualTo(book1.getName());
        assertThat(book.getId()).isEqualTo(book1.getId());
    }

    @Test
    void saveBook_Exception() {
        Book book1 = createBook("1");
        when(bookRepository.save(book1)).thenThrow(SaveBookException.class);
        assertThatThrownBy(() -> bookService.saveBook(book1)).isInstanceOf(SaveBookException.class);
    }

    @Test
    void deleteBook_withId_1() {
        doNothing().when(bookRepository).deleteById("1");
        bookService.deleteBook("1");
        verify(bookRepository,times(1)).deleteById(anyString());
    }

    @Test
    void deleteBook_Exception() {
        Mockito.doThrow(DeleteBookException.class).when(bookRepository).deleteById(anyString());
        assertThatThrownBy(() -> bookService.deleteBook("1")).isInstanceOf(DeleteBookException.class);
    }

    @Test
    void getBookByAuthor_withId_1() {
        Book book1 = createBook("1");
        Book book2 = createBook("2");
        when(bookRepository.getByAuthorId("1")).thenReturn(List.of(book1,book2));
        List<Book> books = bookService.getBookByAuthor("1");
        assertThat(books).contains(book1, book2);
    }

    @Test
    void getBookByGenre_withId_1() {
        Book book1 = createBook("1");
        Book book2 = createBook("2");
        when(bookRepository.getByGenreId("1")).thenReturn(List.of(book1,book2));
        List<Book> books = bookService.getBookByGenre("1");
        assertThat(books).contains(book1, book2);
    }

    @Test
    void getBookByAuthor_Exception() {
        when(bookRepository.getByAuthorId("1")).thenThrow(GetBookByAuthorException.class);
        assertThatThrownBy(() -> bookService.getBookByAuthor("1")).isInstanceOf(GetBookByAuthorException.class);
    }

    @Test
    void getBookByGenre_Exception() {
        when(bookRepository.getByGenreId("1")).thenThrow(GetBookByGenreException.class);
        assertThatThrownBy(() -> bookService.getBookByGenre("1")).isInstanceOf(GetBookByGenreException.class);
    }

    private Book createBook(String id) {
        return new Book("1", "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }

    @Test
    void getByIdWithComments() {
        Book book = createBook(WANTED_BOOK_ID);
        BookComment comment1 = new BookComment("1", "com1" , book);
        BookComment comment2 = new BookComment("2", "com2" , book);
        List<BookComment> comments = List.of(comment1, comment2);
        when(commentRepository.getByBookId(WANTED_BOOK_ID)).thenReturn(comments);
        when(bookRepository.findById(WANTED_BOOK_ID)).thenReturn(Optional.of(book));

        BookWithComments returnedBook = bookService.getByIdWithComments(WANTED_BOOK_ID);

        assertThat(returnedBook).isNotNull()
                .matches(b -> b.getBook() != null)
                .matches(b -> b.getComments().equals(comments));

        Assertions.assertThat(returnedBook.getBook())
                .matches(b -> b.getGenre() != null)
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getId().equals("1"));

    }
}