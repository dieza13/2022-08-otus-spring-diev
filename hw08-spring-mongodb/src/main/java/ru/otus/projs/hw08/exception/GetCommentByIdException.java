package ru.otus.projs.hw08.exception;

import lombok.Getter;

@Getter
public class GetCommentByIdException extends RuntimeException{

    private final String commentId;

    public GetCommentByIdException(String commentId, Throwable ex) {
        super("Get comment with id " + commentId + " exception", ex);
        this.commentId = commentId;
    }

}
