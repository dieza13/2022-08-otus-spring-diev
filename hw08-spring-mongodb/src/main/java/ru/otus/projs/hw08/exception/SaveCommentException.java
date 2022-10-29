package ru.otus.projs.hw08.exception;

import lombok.Getter;
import ru.otus.projs.hw08.model.BookComment;

@Getter
public class SaveCommentException extends RuntimeException{

    private final BookComment bookComment;

    public SaveCommentException(BookComment bookComment, Throwable ex) {
        super(ex);
        this.bookComment = bookComment;
    }

}
