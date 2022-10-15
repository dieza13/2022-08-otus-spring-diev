package ru.otus.projs.hw06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.projs.hw06.exception.service.*;
import ru.otus.projs.hw06.model.Genre;
import ru.otus.projs.hw06.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleGenreService implements GenreService {

    private final GenreRepository genreRepository;

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
            return genreRepository.getById(id).orElseThrow();
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
            genreRepository.delete(id);
        } catch (Exception e) {
            throw new DeleteGenreException(id, e);
        }
    }
}
