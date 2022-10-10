package ru.otus.projs.hw06.exception.service;

import lombok.Getter;
import ru.otus.projs.hw06.model.Comment;

@Getter
public class SaveCommentException extends RuntimeException{

    private final Comment comment;

    public SaveCommentException(Comment comment, Throwable ex) {
        super(ex);
        this.comment = comment;
    }

}
