package ru.otus.projs.hw05.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw05.model.Book;
import ru.otus.projs.hw05.service.BookService;
import ru.otus.projs.hw05.service.CallWithConvertInputService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookController {

    private static final List<String> commands = List.of(
            Commands.commands + "\n",
            Commands.all + "\n",
            Commands.byId + "\n",
            Commands.save + "\n",
            Commands.delete + "\n",
            Commands.byAuthorId);
    private static final String BOOK_IN_JSON_FORMAT = "Введите книгу в формате json:";
    private static final String BOOK_WAS_DELETED = "Книга с id {0} была удалена";

    private final BookService bookService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = Commands.all)
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @ShellMethod(key = Commands.byId)
    public Book getBookById(@ShellOption long bookId) {
        return bookService.getBookById(bookId);
    }

    @ShellMethod(key = Commands.save)
    public Book saveBook() {

        return callWrapperService.callWithConvertInput(
                Book.class,
                BOOK_IN_JSON_FORMAT,
                bookService::saveBook
        );

    }

    @ShellMethod(key = Commands.delete)
    public String deleteBook(@ShellOption long bookId) {
        bookService.deleteBook(bookId);
        return String.format(BOOK_WAS_DELETED, bookId);
    }

    @ShellMethod(key = Commands.byAuthorId)
    public List<Book> getBooksByAuthor(@ShellOption long authorId) {
        return bookService.getBookByAuthor(authorId);
    }

    @ShellMethod(key = Commands.commands)
    public List<String> getBookCommands() {
        return commands;
    }

    private class Commands {
        public static final String commands = "book";
        public static final String all = "book.all";
        public static final String byId = "book.byId";
        public static final String save = "book.save";
        public static final String delete = "book.delete";
        public static final String byAuthorId = "book.byAuthorId";
    }

}
