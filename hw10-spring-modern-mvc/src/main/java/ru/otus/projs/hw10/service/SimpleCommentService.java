package ru.otus.projs.hw10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw10.exception.service.DeleteCommentException;
import ru.otus.projs.hw10.exception.service.FindAllCommentByIdException;
import ru.otus.projs.hw10.exception.service.GetCommentByIdException;
import ru.otus.projs.hw10.exception.service.SaveCommentException;
import ru.otus.projs.hw10.model.BookComment;
import ru.otus.projs.hw10.repository.BookCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleCommentService implements CommentService {

    private final BookCommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> findAllByBookId(Long id) {
        try {
            return commentRepository.findAllByBookId(id);
        } catch (Exception e) {
            throw new FindAllCommentByIdException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public BookComment getCommentById(Long id) {
        try {
            return commentRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetCommentByIdException(id, e);
        }
    }

    @Transactional
    @Override
    public BookComment saveComment(BookComment bookComment) {
        try {
            return commentRepository.save(bookComment);
        } catch (Exception e) {
            throw new SaveCommentException(bookComment, e);
        }
    }

    @Transactional
    @Override
    public void deleteComment(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteCommentException(id, e);
        }
    }
}
