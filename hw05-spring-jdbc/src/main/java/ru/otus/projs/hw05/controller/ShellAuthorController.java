package ru.otus.projs.hw05.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.service.AuthorService;
import ru.otus.projs.hw05.service.CallWithConvertInputService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellAuthorController {

    private static final List<String> commands = List.of(
            Commands.commands + "\n",
            Commands.all + "\n",
            Commands.byId + "\n",
            Commands.save + "\n",
            Commands.delete + "\n"
    );
    private static final String AUTHOR_IN_JSON_FORMAT = "Введите автора в формате json:";
    private static final String AUTHOR_WAS_DELETED = "Автор с id {0} был удален";

    private final AuthorService authorService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = Commands.all)
    public List<Author> findAll() {
        return authorService.findAll();
    }

    @ShellMethod(key = Commands.byId)
    public Author getAuthorById(@ShellOption long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @ShellMethod(key = Commands.save)
    public Author saveAuthor() {

        return callWrapperService.callWithConvertInput(
                Author.class,
                AUTHOR_IN_JSON_FORMAT,
                authorService::saveAuthor
        );

    }

    @ShellMethod(key = Commands.delete)
    public String deleteAuthor(@ShellOption long authorId) {
        authorService.deleteAuthor(authorId);
        return String.format(AUTHOR_WAS_DELETED, authorId);
    }

    @ShellMethod(key = Commands.commands)
    public List<String> getAuthorCommands() {
        return commands;
    }


    private class Commands {
        public static final String commands = "author";
        public static final String all = "author.all";
        public static final String byId = "author.byId";
        public static final String save = "author.save";
        public static final String delete = "author.delete";
    }

}
