package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class DeleteBookException extends RuntimeException{

    private final String id;

    public DeleteBookException(String id, Throwable ex) {
        super("Delete book with id " + id + " exception",ex);
        this.id = id;
    }

}
