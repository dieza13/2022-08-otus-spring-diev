package ru.otus.projs.hw08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw08.exception.DeleteCommentException;
import ru.otus.projs.hw08.exception.FindAllCommentByIdException;
import ru.otus.projs.hw08.exception.GetCommentByIdException;
import ru.otus.projs.hw08.exception.SaveCommentException;
import ru.otus.projs.hw08.model.BookComment;
import ru.otus.projs.hw08.repository.BookCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleCommentService implements CommentService {

    private final BookCommentRepository commentRepository;

    @Override
    public List<BookComment> findAllByBookId(String id) {
        try {
            return commentRepository.getByBookId(id);
        } catch (Exception e) {
            throw new FindAllCommentByIdException(e);
        }
    }

    @Override
    public BookComment getCommentById(String id) {
        try {
            return commentRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetCommentByIdException(id, e);
        }
    }

    @Override
    public BookComment saveComment(BookComment bookComment) {
        try {
            return commentRepository.save(bookComment);
        } catch (Exception e) {
            throw new SaveCommentException(bookComment, e);
        }
    }

    @Override
    public void deleteComment(String id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteCommentException(id, e);
        }
    }
}
