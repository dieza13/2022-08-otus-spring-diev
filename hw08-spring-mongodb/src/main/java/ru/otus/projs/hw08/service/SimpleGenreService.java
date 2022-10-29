package ru.otus.projs.hw08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw08.model.Genre;
import ru.otus.projs.hw08.repository.BookRepository;
import ru.otus.projs.hw08.repository.GenreRepository;
import ru.otus.projs.hw08.exception.DeleteGenreException;
import ru.otus.projs.hw08.exception.FindAllGenreException;
import ru.otus.projs.hw08.exception.GetGenreByIdException;
import ru.otus.projs.hw08.exception.SaveGenreException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleGenreService implements GenreService {
    private static final String ERR_DELETE_GENRE_WITH_BOOKS = "Can't delete genre with books";

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Genre> findAll() {
        try {
            return genreRepository.findAll();
        } catch (Exception e) {
            throw new FindAllGenreException(e);
        }
    }

    @Override
    public Genre getGenreById(String id) {
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

    @Override
    public void deleteGenre(String id) {
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
