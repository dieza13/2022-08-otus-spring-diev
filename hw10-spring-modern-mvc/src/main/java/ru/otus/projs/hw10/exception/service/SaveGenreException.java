package ru.otus.projs.hw10.exception.service;

import lombok.Getter;
import ru.otus.projs.hw10.model.Genre;

@Getter
public class SaveGenreException extends RuntimeException{

    private final Genre genre;

    public SaveGenreException(Genre genre, Throwable ex) {
        super(ex);
        this.genre = genre;
    }

}
