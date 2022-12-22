package ru.otus.projs.hw13.service;

import lombok.Getter;
import ru.otus.projs.hw13.model.Author;

@Getter
public class SaveAuthorException extends RuntimeException{

    private final Author author;

    public SaveAuthorException(Author author, Throwable ex) {
        super(ex);
        this.author = author;
    }

}
