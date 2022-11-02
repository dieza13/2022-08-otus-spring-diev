package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class DeleteAuthorException extends RuntimeException{

    private final String id;

    public DeleteAuthorException(String id, Throwable ex) {
        super("Delete author with id " + id + " exception: " + ex.getMessage(), ex);
        this.id = id;
    }

}
