package ru.otus.projs.hw10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw10.exception.service.DeleteGenreException;
import ru.otus.projs.hw10.exception.service.FindAllGenreException;
import ru.otus.projs.hw10.exception.service.GetGenreByIdException;
import ru.otus.projs.hw10.model.Genre;
import ru.otus.projs.hw10.repository.BookRepository;
import ru.otus.projs.hw10.repository.GenreRepository;
import ru.otus.projs.hw10.exception.service.SaveGenreException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleGenreService implements GenreService {
    private static final String ERR_DELETE_GENRE_WITH_BOOKS = "Can't delete genre with books";

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        try {
            return genreRepository.findAll();
        } catch (Exception e) {
            throw new FindAllGenreException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getGenreById(Long id) {
        try {
            return genreRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new GetGenreByIdException(id, e);
        }
    }

    @Transactional
    @Override
    public Genre saveGenre(Genre genre) {
        try {
            return genreRepository.save(genre);
        } catch (Exception e) {
            throw new SaveGenreException(genre, e);
        }
    }

    @Transactional
    @Override
    public void deleteGenre(long id) {
        try {
            if (bookRepository.countByGenreId(id) > 0) {
                throw new RuntimeException(ERR_DELETE_GENRE_WITH_BOOKS);
            }
            genreRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteGenreException(id, e);
        }
    }
}
