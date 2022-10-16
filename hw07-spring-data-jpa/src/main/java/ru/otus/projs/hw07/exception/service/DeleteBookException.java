package ru.otus.projs.hw07.exception.service;

import lombok.Getter;

@Getter
public class DeleteBookException extends RuntimeException{

    private final long id;

    public DeleteBookException(long id, Throwable ex) {
        super("Delete book with id " + id + " exception",ex);
        this.id = id;
    }

}
