package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class GetAuthorByIdException extends RuntimeException{

    private final String authorId;

    public GetAuthorByIdException(String authorId, Throwable ex) {
        super("Get author with id " + authorId + " exception", ex);
        this.authorId = authorId;
    }

}
