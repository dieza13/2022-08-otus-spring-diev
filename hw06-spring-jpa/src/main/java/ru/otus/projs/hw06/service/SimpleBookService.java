package ru.otus.projs.hw06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw06.exception.service.DeleteBookException;
import ru.otus.projs.hw06.exception.service.FindAllBookException;
import ru.otus.projs.hw06.exception.service.GetBookByAuthorException;
import ru.otus.projs.hw06.exception.service.GetBookByIdException;
import ru.otus.projs.hw06.exception.service.SaveBookException;
import ru.otus.projs.hw06.model.Book;
import ru.otus.projs.hw06.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {

    private final BookRepository bookRepository;

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
            return bookRepository.getById(id).orElseThrow();
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
            bookRepository.delete(id);
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
}
