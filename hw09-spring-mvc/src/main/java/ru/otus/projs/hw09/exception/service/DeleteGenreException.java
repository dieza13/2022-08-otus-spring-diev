package ru.otus.projs.hw09.exception.service;

import lombok.Getter;

@Getter
public class DeleteGenreException extends RuntimeException{

    private final long id;

    public DeleteGenreException(long id, Throwable ex) {
        super("Delete genre with id " + id + " exception", ex);
        this.id = id;
    }

}
