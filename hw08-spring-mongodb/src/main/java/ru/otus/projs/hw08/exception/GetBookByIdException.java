package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class GetBookByIdException extends RuntimeException{

    private final String bookId;

    public GetBookByIdException(String bookId, Throwable ex) {
        super("Get book with id " + bookId + " exception",ex);
        this.bookId = bookId;
    }

}
