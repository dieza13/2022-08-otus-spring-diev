package ru.otus.projs.hw07.exception.service;

import lombok.Getter;

@Getter
public class GetCommentByIdException extends RuntimeException{

    private final Long commentId;

    public GetCommentByIdException(Long commentId, Throwable ex) {
        super("Get comment with id " + commentId + " exception", ex);
        this.commentId = commentId;
    }

}
