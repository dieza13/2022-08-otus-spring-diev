package ru.otus.projs.hw05.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw05.model.Genre;
import ru.otus.projs.hw05.service.CallWithConvertInputService;
import ru.otus.projs.hw05.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellGenreController {

    private static final List<String> commands = List.of(
            Commands.commands + "\n",
            Commands.all + "\n",
            Commands.byId + "\n",
            Commands.save + "\n",
            Commands.delete + "\n"
    );
    private static final String GENRE_IN_JSON_FORMAT = "Введите жанр в формате json:";
    private static final String GENRE_WAS_DELETED = "Жанр с id {0} был удален";

    private final GenreService genreService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = Commands.all)
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @ShellMethod(key = Commands.byId)
    public Genre getGenreById(@ShellOption long genreId) {
        return genreService.getGenreById(genreId);
    }

    @ShellMethod(key = Commands.save)
    public Genre saveGenre() {

        return callWrapperService.callWithConvertInput(
                Genre.class,
                GENRE_IN_JSON_FORMAT,
                genreService::saveGenre
        );

    }

    @ShellMethod(key = Commands.delete)
    public String deleteGenre(@ShellOption long genreId) {
        genreService.deleteGenre(genreId);
        return String.format(GENRE_WAS_DELETED, genreId);
    }

    @ShellMethod(key = Commands.commands)
    public List<String> getGenreCommands() {
        return commands;
    }


    private class Commands {
        public static final String commands = "genre";
        public static final String all = "genre.all";
        public static final String byId = "genre.byId";
        public static final String save = "genre.save";
        public static final String delete = "genre.delete";
    }

}
