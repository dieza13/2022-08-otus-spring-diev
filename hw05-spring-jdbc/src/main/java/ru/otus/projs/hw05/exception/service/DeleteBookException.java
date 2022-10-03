package ru.otus.projs.hw05.exception.service;

import lombok.Getter;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.model.Book;

import java.util.Optional;

@Getter
public class DeleteBookException extends RuntimeException{

    private final long id;

    public DeleteBookException(long id, Throwable ex) {
        super("Delete book with id " + id + " exception",ex);
        this.id = id;
    }

}
