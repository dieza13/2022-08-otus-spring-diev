package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class GetBookByAuthorException extends RuntimeException{

    private final String id;

    public GetBookByAuthorException(String id, Throwable ex) {
        super("Get books with author id " + id + " exception", ex);
        this.id = id;
    }

}
