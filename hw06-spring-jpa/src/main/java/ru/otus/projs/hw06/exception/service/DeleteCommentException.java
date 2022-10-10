package ru.otus.projs.hw06.exception.service;

import lombok.Getter;

@Getter
public class DeleteCommentException extends RuntimeException{

    private final long id;

    public DeleteCommentException(long id, Throwable ex) {
        super("Delete comment with id " + id + " exception", ex);
        this.id = id;
    }

}
