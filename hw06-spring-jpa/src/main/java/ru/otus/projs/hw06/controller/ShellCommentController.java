package ru.otus.projs.hw06.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw06.model.BookComment;
import ru.otus.projs.hw06.service.CallWithConvertInputService;
import ru.otus.projs.hw06.service.CommentService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommentController {

    private static final String COMMENT_IN_JSON_FORMAT = "Введите комментарий в формате json:";
    private static final String COMMENT_WAS_DELETED = "Комментарий с id {0} был удален";

    private final CommentService commentService;
    private final CallWithConvertInputService callWrapperService;


    @ShellMethod(key = "comment.allByBookId")
    public List<BookComment> findAllByBookId(@ShellOption long bookId) {
        return commentService.findAllByBookId(bookId);
    }

    @ShellMethod(key = "comment.byId")
    public BookComment getCommentById(@ShellOption long commentId) {
        return commentService.getCommentById(commentId);
    }

    @ShellMethod(key = "comment.save")
    public BookComment saveComment() {

        return callWrapperService.callWithConvertInput(
                BookComment.class,
                COMMENT_IN_JSON_FORMAT,
                commentService::saveComment
        );

    }

    @ShellMethod(key = "comment.delete")
    public String deleteComment(@ShellOption long commentId) {
        commentService.deleteComment(commentId);
        return String.format(COMMENT_WAS_DELETED, commentId);
    }

}
