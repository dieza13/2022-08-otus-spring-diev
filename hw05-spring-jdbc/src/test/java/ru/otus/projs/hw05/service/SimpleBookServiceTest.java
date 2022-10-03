package ru.otus.projs.hw05.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw05.dao.BookDao;
import ru.otus.projs.hw05.exception.service.*;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.model.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SimpleBookService.class})
@DisplayName("Сервис по работе с Book")
class SimpleBookServiceTest {

    @Autowired
    private SimpleBookService bookService;
    @MockBean
    private BookDao bookDao;

    @Test
    void findAll_ok() {
        Book book1 = new Book(1l, "Book1");
        Book book2 = new Book(2l, "Book2");
        when(bookDao.findAll()).thenReturn(List.of(book1, book2));

        List<Book> books = bookService.findAll();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books).contains(book1,book2);
    }

    @Test
    void findAll_Exception() {
        when(bookDao.findAll()).thenThrow(FindAllBookException.class);
        assertThatThrownBy(() -> bookService.findAll()).isInstanceOf(FindAllBookException.class);
    }

    @Test
    void getBookById_1() {
        Book book1 = new Book(1l, "Book1");
        when(bookDao.getById(1l)).thenReturn(book1);
        Book book = bookService.getBookById(1l);
        assertThat(book.getName()).isEqualTo("Book1");
    }

    @Test
    void getBookById_Exception() {
        when(bookDao.getById(1l)).thenThrow(GetBookByIdException.class);
        assertThatThrownBy(() -> bookService.getBookById(1l)).isInstanceOf(GetBookByIdException.class);
    }

    @Test
    void saveBook_withIdAndName() {
        Book book1 = new Book(1l, "Book1");
        when(bookDao.save(book1)).thenReturn(book1);
        Book book = bookService.saveBook(book1);
        assertThat(book.getName()).isEqualTo("Book1");
        assertThat(book.getId()).isEqualTo(1l);
    }

    @Test
    void saveBook_Exception() {
        Book book1 = new Book(1l, "Book1");
        when(bookDao.save(book1)).thenThrow(SaveBookException.class);
        assertThatThrownBy(() -> bookService.saveBook(book1)).isInstanceOf(SaveBookException.class);
    }

    @Test
    void deleteBook_withId_1() {
        doNothing().when(bookDao).delete(1l);
        bookService.deleteBook(1l);
        verify(bookDao,times(1)).delete(anyLong());
    }

    @Test
    void deleteBook_Exception() {
        doThrow(DeleteBookException.class).when(bookDao).delete(anyLong());
        assertThatThrownBy(() -> bookService.deleteBook(1l)).isInstanceOf(DeleteBookException.class);
    }

    @Test
    void getBookByAuthor_withId_1() {
        Book book1 = new Book(1l, "Book1", new Author(1l,null,null), null);
        Book book2 = new Book(2l, "Book2", new Author(1l,null,null), null);
        when(bookDao.getByAuthorId(1l)).thenReturn(List.of(book1,book2));
        List<Book> books = bookService.getBookByAuthor(1l);
        assertThat(books).contains(book1, book2);
    }

    @Test
    void getBookByAuthor_Exception() {
        when(bookDao.getByAuthorId(1l)).thenThrow(GetBookByAuthorException.class);
        assertThatThrownBy(() -> bookService.getBookByAuthor(1l)).isInstanceOf(GetBookByAuthorException.class);
    }
}