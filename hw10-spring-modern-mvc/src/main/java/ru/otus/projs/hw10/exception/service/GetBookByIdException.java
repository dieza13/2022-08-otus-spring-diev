package ru.otus.projs.hw10.exception.service;

import lombok.Getter;

@Getter
public class GetBookByIdException extends RuntimeException{

    private final Long bookId;

    public GetBookByIdException(Long bookId, Throwable ex) {
        super("Get book with id " + bookId + " exception",ex);
        this.bookId = bookId;
    }

}
