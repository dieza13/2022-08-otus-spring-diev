package ru.otus.projs.hw06.exception.service;

import lombok.Getter;

@Getter
public class DeleteAuthorException extends RuntimeException{

    private final Long id;

    public DeleteAuthorException(Long id, Throwable ex) {
        super("Delete author with id " + id + " exception", ex);
        this.id = id;
    }

}
