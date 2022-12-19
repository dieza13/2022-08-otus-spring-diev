package ru.otus.projs.hw13.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw13.model.Author;
import ru.otus.projs.hw13.model.Book;
import ru.otus.projs.hw13.model.Genre;
import ru.otus.projs.hw13.repository.BookRepository;

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

    private static final long WANTED_BOOK_ID = 1;

    @Test
    void findAll_ok() {
        Book book1 = createBook(1);
        Book book2 = createBook(2);
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
        Book book1 = createBook(1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        Book book = bookService.getBookById(1L);
        assertThat(book.getId()).isEqualTo(book1.getId());
    }

    @Test
    void getBookById_Exception() {
        when(bookRepository.findById(1L)).thenThrow(GetBookByIdException.class);
        assertThatThrownBy(() -> bookService.getBookById(1L)).isInstanceOf(GetBookByIdException.class);
    }

    @Test
    void saveBook_withIdAndName() {
        Book book1 = createBook(1);
        when(bookRepository.save(book1)).thenReturn(book1);
        Book book = bookService.saveBook(book1);
        assertThat(book.getName()).isEqualTo(book1.getName());
        assertThat(book.getId()).isEqualTo(book1.getId());
    }

    @Test
    void saveBook_Exception() {
        Book book1 = createBook(1);
        when(bookRepository.save(book1)).thenThrow(SaveBookException.class);
        assertThatThrownBy(() -> bookService.saveBook(book1)).isInstanceOf(SaveBookException.class);
    }

    @Test
    void deleteBook_withId_1() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteBook(1L);
        verify(bookRepository,times(1)).deleteById(anyLong());
    }

    @Test
    void deleteBook_Exception() {
        Mockito.doThrow(DeleteBookException.class).when(bookRepository).deleteById(anyLong());
        assertThatThrownBy(() -> bookService.deleteBook(1L)).isInstanceOf(DeleteBookException.class);
    }

    @Test
    void getBookByAuthor_withId_1() {
        Book book1 = createBook(1);
        Book book2 = createBook(2);
        when(bookRepository.getByAuthorId(1L)).thenReturn(List.of(book1,book2));
        List<Book> books = bookService.getBookByAuthor(1L);
        assertThat(books).contains(book1, book2);
    }

    @Test
    void getBookByAuthor_Exception() {
        when(bookRepository.getByAuthorId(1L)).thenThrow(GetBookByAuthorException.class);
        assertThatThrownBy(() -> bookService.getBookByAuthor(1L)).isInstanceOf(GetBookByAuthorException.class);
    }

    private Book createBook(long id) {
        return new Book(1L, "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }


}