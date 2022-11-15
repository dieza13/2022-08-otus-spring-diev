package ru.otus.projs.hw09.exception.service;

import lombok.Getter;

@Getter
public class GetBookByAuthorException extends RuntimeException{

    private final long id;

    public GetBookByAuthorException(long id, Throwable ex) {
        super("Get books with author id " + id + " exception", ex);
        this.id = id;
    }

}
