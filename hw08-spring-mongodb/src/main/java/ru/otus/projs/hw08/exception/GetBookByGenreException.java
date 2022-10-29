package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class GetBookByGenreException extends RuntimeException{

    private final String id;

    public GetBookByGenreException(String id, Throwable ex) {
        super("Get books with genre id " + id + " exception", ex);
        this.id = id;
    }

}
