package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class DeleteGenreException extends RuntimeException{

    private final String id;

    public DeleteGenreException(String id, Throwable ex) {
        super("Delete genre with id " + id + " exception: " + ex.getMessage(), ex);
        this.id = id;
    }

}
