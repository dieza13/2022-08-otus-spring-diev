package ru.otus.projs.hw05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw05.dao.AuthorDao;
import ru.otus.projs.hw05.exception.service.*;
import ru.otus.projs.hw05.model.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleAuthorService implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public List<Author> findAll() {
        try {
            return authorDao.findAll();
        } catch (Exception e) {
            throw new FindAllAuthorException(e);
        }
    }

    @Override
    public Author getAuthorById(Long id) {
        try {
            return authorDao.getById(id);
        } catch (Exception e) {
            throw new GetAuthorByIdException(id, e);
        }
    }

    @Override
    public Author saveAuthor(Author author) {
        try {
            return authorDao.save(author);
        } catch (Exception e) {
            throw new SaveAuthorException(author, e);
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        try {
            authorDao.delete(id);
        } catch (Exception e) {
            throw new DeleteAuthorException(id, e);
        }
    }
}
