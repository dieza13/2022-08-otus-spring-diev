package ru.otus.projs.hw08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw08.dto.BookWithComments;
import ru.otus.projs.hw08.model.Book;
import ru.otus.projs.hw08.model.BookComment;
import ru.otus.projs.hw08.repository.BookCommentRepository;
import ru.otus.projs.hw08.repository.BookRepository;
import ru.otus.projs.hw08.exception.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {

    private final BookRepository bookRepository;
    private final BookCommentRepository commentRepository;

    @Override
    public List<Book> findAll() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new FindAllBookException(e);
        }
    }

    @Override
    public Book getBookById(String id) {
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

    @Override
    public void deleteBook(String id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Override
    public List<Book> getBookByAuthor(String id) {
        try {
            return bookRepository.getByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }

    @Override
    public List<Book> getBookByGenre(String id) {
        try {
            return bookRepository.getByGenreId(id);
        } catch (Exception e) {
            throw new GetBookByGenreException(id, e);
        }
    }

    @Override
    public BookWithComments getByIdWithComments(String id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
            List<BookComment> comments = commentRepository.getByBookId(id);
            return new BookWithComments(book, comments);
        } catch (Exception e) {
            throw new GetBookByIdWithCommentsException(id, e);
        }
    }

}
