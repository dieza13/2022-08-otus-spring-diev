package ru.otus.projs.hw08.exception;

import lombok.Getter;
import ru.otus.projs.hw08.model.Author;

@Getter
public class SaveAuthorException extends RuntimeException{

    private final Author author;

    public SaveAuthorException(Author author, Throwable ex) {
        super(ex);
        this.author = author;
    }

}
