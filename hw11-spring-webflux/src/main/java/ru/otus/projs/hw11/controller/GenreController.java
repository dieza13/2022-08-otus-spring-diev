package ru.otus.projs.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.projs.hw11.model.Author;
import ru.otus.projs.hw11.model.Genre;
import ru.otus.projs.hw11.repository.BookRepository;
import ru.otus.projs.hw11.repository.GenreRepository;
import ru.otus.projs.hw11.service.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @GetMapping(path = "/api/genre")
    public Flux<Genre> genreList() {
        return genreRepository.findAll();
    }

    @GetMapping(path = "/api/genre/{id}")
    public Mono<Genre> getGenre(@PathVariable("id") String id) {
        return genreRepository.findById(id);
    }

    @PostMapping(path = "/api/genre")
    public Mono<ResponseEntity<Genre>> saveGenre(@RequestBody Genre genre) {
        return genreRepository.save(genreService.prepareGenre4Save(genre))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(path = "/api/genre/{id}")
    public Mono<ResponseEntity<Void>> deleteGenre(@PathVariable("id") String genreId) {
        return bookRepository.countByGenreId(genreId)
                .filter(id -> id == 0)
                .flatMap(cnt -> genreRepository.deleteById(genreId).map(ResponseEntity::ok))
                .switchIfEmpty(Mono.fromCallable(()->ResponseEntity.unprocessableEntity().build()));
    }

}
