package ru.otus.projs.hw06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw06.exception.service.DeleteAuthorException;
import ru.otus.projs.hw06.exception.service.FindAllAuthorException;
import ru.otus.projs.hw06.exception.service.GetAuthorByIdException;
import ru.otus.projs.hw06.exception.service.SaveAuthorException;
import ru.otus.projs.hw06.model.Author;
import ru.otus.projs.hw06.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        try {
            return authorRepository.findAll();
        } catch (Exception e) {
            throw new FindAllAuthorException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Author getAuthorById(Long id) {
        try {
            return authorRepository.getById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetAuthorByIdException(id, e);
        }
    }

    @Transactional
    @Override
    public Author saveAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (Exception e) {
            throw new SaveAuthorException(author, e);
        }
    }

    @Transactional
    @Override
    public void deleteAuthor(Long id) {
        try {
            authorRepository.delete(id);
        } catch (Exception e) {
            throw new DeleteAuthorException(id, e);
        }
    }
}