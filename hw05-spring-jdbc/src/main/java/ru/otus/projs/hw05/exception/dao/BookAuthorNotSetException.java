package ru.otus.projs.hw05.exception.dao;

import lombok.Getter;

@Getter
public class BookAuthorNotSetException extends RuntimeException{

    public BookAuthorNotSetException() {
        super("Book author not set");
    }
    public BookAuthorNotSetException(Throwable ex) {
        super("Book author not set", ex);
    }

}
