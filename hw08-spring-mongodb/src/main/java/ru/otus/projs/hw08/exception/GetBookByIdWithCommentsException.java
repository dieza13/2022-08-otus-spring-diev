package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class GetBookByIdWithCommentsException extends RuntimeException{

    private final String bookId;

    public GetBookByIdWithCommentsException(String bookId, Throwable ex) {
        super("Get book with id " + bookId + " exception",ex);
        this.bookId = bookId;
    }

}
