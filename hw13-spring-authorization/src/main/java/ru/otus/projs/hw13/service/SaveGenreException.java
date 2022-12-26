package ru.otus.projs.hw13.service;

import lombok.Getter;
import ru.otus.projs.hw13.model.Genre;

@Getter
public class SaveGenreException extends RuntimeException{

    private final Genre genre;

    public SaveGenreException(Genre genre, Throwable ex) {
        super(ex);
        this.genre = genre;
    }

}
