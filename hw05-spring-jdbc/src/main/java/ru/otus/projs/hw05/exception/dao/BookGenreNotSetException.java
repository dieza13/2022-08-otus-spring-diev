package ru.otus.projs.hw05.exception.dao;

import lombok.Getter;

@Getter
public class BookGenreNotSetException extends RuntimeException{

    public BookGenreNotSetException() {
        super("Book genre not set");
    }
    public BookGenreNotSetException(Throwable ex) {
        super("Book genre not set", ex);
    }

}
