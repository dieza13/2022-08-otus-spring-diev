package ru.otus.projs.hw05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw05.dao.GenreDao;
import ru.otus.projs.hw05.exception.service.*;
import ru.otus.projs.hw05.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleGenreService implements GenreService {

    private final GenreDao genreDao;

    @Override
    public List<Genre> findAll() {
        try {
            return genreDao.findAll();
        } catch (Exception e) {
            throw new FindAllGenreException(e);
        }
    }

    @Override
    public Genre getGenreById(Long id) {
        try {
            return genreDao.getById(id);
        } catch (Exception e) {
            throw new GetGenreByIdException(id, e);
        }
    }

    @Override
    public Genre saveGenre(Genre genre) {
        try {
            return genreDao.save(genre);
        } catch (Exception e) {
            throw new SaveGenreException(genre, e);
        }
    }

    @Override
    public void deleteGenre(long id) {
        try {
            genreDao.delete(id);
        } catch (Exception e) {
            throw new DeleteGenreException(id, e);
        }
    }
}
