package ru.otus.projs.hw05.exception.service;

import lombok.Getter;
import ru.otus.projs.hw05.model.Author;

@Getter
public class SaveAuthorException extends RuntimeException{

    private final Author author;

    public SaveAuthorException(Author author, Throwable ex) {
        super(ex);
        this.author = author;
    }

}
