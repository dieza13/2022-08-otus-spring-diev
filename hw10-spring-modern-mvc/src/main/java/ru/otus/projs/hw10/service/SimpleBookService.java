package ru.otus.projs.hw10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw10.dto.BookWithComments;
import ru.otus.projs.hw10.exception.service.*;
import ru.otus.projs.hw10.model.Book;
import ru.otus.projs.hw10.model.BookComment;
import ru.otus.projs.hw10.repository.BookCommentRepository;
import ru.otus.projs.hw10.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {

    private final BookRepository bookRepository;
    private final BookCommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new FindAllBookException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(Long id) {
        try {
            return bookRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetBookByIdException(id, e);
        }
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @Transactional
    @Override
    public void deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByAuthor(long id) {
        try {
            return bookRepository.getByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }

    @Override
    public BookWithComments getByIdWithComments(Long id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
            List<BookComment> comments = commentRepository.findAllByBookId(id);
            return new BookWithComments(book, comments);
        } catch (Exception e) {
            throw new GetBookByIdWithCommentsException(id, e);
        }
    }

}
