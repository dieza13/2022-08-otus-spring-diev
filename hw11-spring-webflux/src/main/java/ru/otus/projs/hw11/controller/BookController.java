package ru.otus.projs.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.projs.hw11.model.Author;
import ru.otus.projs.hw11.model.Book;
import ru.otus.projs.hw11.model.Genre;
import ru.otus.projs.hw11.model.dto.BookToSave;
import ru.otus.projs.hw11.repository.AuthorRepository;
import ru.otus.projs.hw11.repository.BookRepository;
import ru.otus.projs.hw11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping(path = "/api/book")
    public Flux<Book> bookList() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/api/book/{id}")
    public Mono<BookToSave> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id).flatMap(b -> Mono.just(BookToSave.toDto(b)));
    }

    @PostMapping(path = "/api/book")
    public Mono<ResponseEntity<Book>> saveBook(@RequestBody BookToSave book) {

        Mono<Author> authorMono = authorRepository.findById(book.getAuthorId());
        Mono<Genre> genreMono = genreRepository.findById(book.getGenreId());
        Mono<BookToSave> bookMono = Mono.just(book);

        return Mono.zip(bookMono,authorMono,genreMono)
                .map(l -> l.getT1().toDomain( l.getT2(), l.getT3()))
                .flatMap(b -> bookRepository.save(prepareBook4Save(b)))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(path = "/api/book/{id}")
    public Mono<ResponseEntity<?>> deleteBook(@PathVariable("id") String bookId) {
        return bookRepository.deleteById(bookId)
                .map(ResponseEntity::ok);
    }

    private Book prepareBook4Save(Book book) {
        book.setId(book.getId().equals("") ? null : book.getId());
        return book;
    }

}
