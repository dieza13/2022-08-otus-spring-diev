package ru.otus.projs.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.projs.hw12.model.Genre;
import ru.otus.projs.hw12.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(path = "/api/genre")
    public List<Genre> genreList() {
        return genreService.findAll();
    }

    @GetMapping(path = "/api/genre/{id}")
    public Genre getGenre(@PathVariable("id") long id) {
        return genreService.getGenreById(id);
    }

    @PostMapping(path = "/api/genre")
    public ResponseEntity<Genre> saveGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreService.saveGenre(genre);
        genre.setId(savedGenre.getId());
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping(path = "/api/genre/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable("id") long genreId) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.ok().build();
    }

}
