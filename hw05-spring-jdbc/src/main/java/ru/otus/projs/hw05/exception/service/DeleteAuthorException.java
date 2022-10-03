package ru.otus.projs.hw05.exception.service;

import lombok.Getter;
import ru.otus.projs.hw05.model.Author;

import java.util.Optional;

@Getter
public class DeleteAuthorException extends RuntimeException{

    private final Long id;

    public DeleteAuthorException(Long id, Throwable ex) {
        super("Delete author with id " + id + " exception", ex);
        this.id = id;
    }

}
