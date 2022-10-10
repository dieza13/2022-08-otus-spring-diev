package ru.otus.projs.hw06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw06.exception.service.DeleteCommentException;
import ru.otus.projs.hw06.exception.service.FindAllCommentByIdException;
import ru.otus.projs.hw06.exception.service.GetCommentByIdException;
import ru.otus.projs.hw06.exception.service.SaveCommentException;
import ru.otus.projs.hw06.model.Comment;
import ru.otus.projs.hw06.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(Long id) {
        try {
            return commentRepository.findAllByBookId(id);
        } catch (Exception e) {
            throw new FindAllCommentByIdException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getCommentById(Long id) {
        try {
            return commentRepository.getById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetCommentByIdException(id, e);
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            throw new SaveCommentException(comment, e);
        }
    }

    @Transactional
    @Override
    public void deleteComment(long id) {
        try {
            commentRepository.delete(id);
        } catch (Exception e) {
            throw new DeleteCommentException(id, e);
        }
    }
}
