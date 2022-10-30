package ru.otus.projs.hw08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw08.exception.DeleteAuthorException;
import ru.otus.projs.hw08.exception.FindAllAuthorException;
import ru.otus.projs.hw08.exception.GetAuthorByIdException;
import ru.otus.projs.hw08.exception.SaveAuthorException;
import ru.otus.projs.hw08.model.Author;
import ru.otus.projs.hw08.repository.AuthorRepository;
import ru.otus.projs.hw08.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleAuthorService implements AuthorService {

    private static final String ERR_DELETE_AUTHOR_WITH_BOOKS = "Can't delete author with books";

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Author> findAll() {
        try {
            return authorRepository.findAll();
        } catch (Exception e) {
            throw new FindAllAuthorException(e);
        }
    }

    @Override
    public Author getAuthorById(String id) {
        try {
            return authorRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetAuthorByIdException(id, e);
        }
    }

    @Override
    public Author saveAuthor(Author author) {
        try {
            if (author.getId() != null) {
                // можно так
                // mongoTemplate.updateMulti(query(where("author.id").is(author.getId())), update("author", author), Book.class);
                // но воспользуемся методом репозитория
                bookRepository.updateAllByAuthorId(author.getId(), author);
            }
            return authorRepository.save(author);
        } catch (Exception e) {
            throw new SaveAuthorException(author, e);
        }
    }

    @Override
    public void deleteAuthor(String id) {
        try {
            if (bookRepository.countByAuthorId(id) > 0) {
                throw new RuntimeException(ERR_DELETE_AUTHOR_WITH_BOOKS);
            }
            authorRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteAuthorException(id, e);
        }
    }
}
