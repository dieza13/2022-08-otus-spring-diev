package ru.otus.projs.hw11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.projs.hw11.model.Book;
import ru.otus.projs.hw11.repository.BookRepository;
import ru.otus.projs.hw11.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping(path = "/api/book")
    public Flux<Book> bookList() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/api/book/{id}")
    public Mono<Book> getBook(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @PostMapping(path = "/api/book")
    public Mono<ResponseEntity<Book>> saveBook( @RequestBody Book book) {
        return bookRepository.save(bookService.prepareBook4Save(book))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(path = "/api/book/{id}")
    public Mono<ResponseEntity<?>> deleteBook(@PathVariable("id") String bookId) {
        return bookRepository.deleteById(bookId)
                .map(ResponseEntity::ok);
    }

}
