package ru.otus.projs.hw05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw05.dao.BookDao;
import ru.otus.projs.hw05.exception.service.*;
import ru.otus.projs.hw05.model.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleBookService implements BookService {

    private final BookDao bookDao;

    @Override
    public List<Book> findAll() {
        try {
            return bookDao.findAll();
        } catch (Exception e) {
            throw new FindAllBookException(e);
        }
    }

    @Override
    public Book getBookById(Long id) {
        try {
            return bookDao.getById(id);
        } catch (Exception e) {
            throw new GetBookByIdException(id, e);
        }
    }

    @Override
    public Book saveBook(Book book) {
        try {
            return bookDao.save(book);
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @Override
    public void deleteBook(long id) {
        try {
            bookDao.delete(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Override
    public List<Book> getBookByAuthor(long id) {
        try {
            return bookDao.getByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }
}
