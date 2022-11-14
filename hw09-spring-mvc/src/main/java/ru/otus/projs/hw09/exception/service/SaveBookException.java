package ru.otus.projs.hw09.exception.service;

import lombok.Getter;
import ru.otus.projs.hw09.model.Book;

@Getter
public class SaveBookException extends RuntimeException{

    private final Book book;

    public SaveBookException(Book book,Throwable ex) {
        super(ex);
        this.book = book;
    }

}
