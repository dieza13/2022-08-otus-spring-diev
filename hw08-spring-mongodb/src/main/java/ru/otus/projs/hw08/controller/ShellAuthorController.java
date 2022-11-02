package ru.otus.projs.hw08.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw08.model.Author;
import ru.otus.projs.hw08.service.AuthorService;
import ru.otus.projs.hw08.service.CallWithConvertInputService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellAuthorController {

    private static final String AUTHOR_IN_JSON_FORMAT = "Введите автора в формате json:";
    private static final String AUTHOR_WAS_DELETED = "Автор с id %s был удален";

    private final AuthorService authorService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = "author.all")
    public List<Author> findAll() {
        return authorService.findAll();
    }

    @ShellMethod(key = "author.byId")
    public Author getAuthorById(@ShellOption String authorId) {
        return authorService.getAuthorById(authorId);
    }

    @ShellMethod(key = "author.save")
    public Author saveAuthor() {

        return callWrapperService.callWithConvertInput(Author.class, AUTHOR_IN_JSON_FORMAT, authorService::saveAuthor);

    }

    @ShellMethod(key = "author.delete")
    public String deleteAuthor(@ShellOption String authorId) {
        authorService.deleteAuthor(authorId);
        return String.format(AUTHOR_WAS_DELETED, authorId);
    }

}
