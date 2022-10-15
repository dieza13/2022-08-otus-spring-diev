package ru.otus.projs.hw06.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw06.model.Genre;
import ru.otus.projs.hw06.service.CallWithConvertInputService;
import ru.otus.projs.hw06.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellGenreController {

    private static final String GENRE_IN_JSON_FORMAT = "Введите жанр в формате json:";
    private static final String GENRE_WAS_DELETED = "Жанр с id %d был удален";

    private final GenreService genreService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = "genre.all")
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @ShellMethod(key = "genre.byId")
    public Genre getGenreById(@ShellOption long genreId) {
        return genreService.getGenreById(genreId);
    }

    @ShellMethod(key = "genre.save")
    public Genre saveGenre() {

        return callWrapperService.callWithConvertInput(Genre.class, GENRE_IN_JSON_FORMAT, genreService::saveGenre);

    }

    @ShellMethod(key = "genre.delete")
    public String deleteGenre(@ShellOption long genreId) {
        genreService.deleteGenre(genreId);
        return String.format(GENRE_WAS_DELETED, genreId);
    }

}
