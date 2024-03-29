package ru.otus.projs.hw18.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw18.exception.service.*;
import ru.otus.projs.hw18.model.Book;
import ru.otus.projs.hw18.repository.BookRepository;

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





}
