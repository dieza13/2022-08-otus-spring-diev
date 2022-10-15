package ru.otus.projs.hw06.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw06.model.Book;
import ru.otus.projs.hw06.service.BookService;
import ru.otus.projs.hw06.service.CallWithConvertInputService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookController {

    private static final String BOOK_IN_JSON_FORMAT = "Введите книгу в формате json:";
    private static final String BOOK_WAS_DELETED = "Книга с id %d была удалена";

    private final BookService bookService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = "book.all")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @ShellMethod(key = "book.byId")
    public Book getBookById(@ShellOption long bookId) {
        return bookService.getBookById(bookId);
    }

    @ShellMethod(key = "book.save")
    public Book saveBook() {

        return callWrapperService.callWithConvertInput(Book.class, BOOK_IN_JSON_FORMAT, bookService::saveBook);

    }

    @ShellMethod(key = "book.delete")
    public String deleteBook(@ShellOption long bookId) {
        bookService.deleteBook(bookId);
        return String.format(BOOK_WAS_DELETED, bookId);
    }

    @ShellMethod(key = "book.byAuthorId")
    public List<Book> getBooksByAuthor(@ShellOption long authorId) {
        return bookService.getBookByAuthor(authorId);
    }

}
