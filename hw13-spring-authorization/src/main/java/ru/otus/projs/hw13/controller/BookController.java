package ru.otus.projs.hw13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.projs.hw13.model.Book;
import ru.otus.projs.hw13.model.dto.BookToSave;
import ru.otus.projs.hw13.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/api/book")
    public List<Book> bookList() {
        return bookService.findAll();
    }

    @GetMapping(path = "/api/book/{id}")
    public Book getBook(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }

    @PostMapping(path = "/api/book")
    public ResponseEntity<BookToSave> saveBook( @RequestBody BookToSave book) {
        Book savedBook = bookService.saveBook(book.toDomain());
        book.setId(savedBook.getId());
        return ResponseEntity.ok(book);
    }

    @DeleteMapping(path = "/api/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

}
