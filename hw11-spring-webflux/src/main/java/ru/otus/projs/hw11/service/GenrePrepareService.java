package ru.otus.projs.hw11.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw11.model.Genre;

@Service
public class GenrePrepareService implements GenreService {
    @Override
    public Genre prepareGenre4Save(Genre genre) {
        genre.setId(genre.getId().equals("") ? null : genre.getId());
        return genre;
    }
}
