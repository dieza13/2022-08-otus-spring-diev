package ru.otus.projs.hw18.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.projs.hw18.model.Book;
import ru.otus.projs.hw18.model.dto.BookToSave;
import ru.otus.projs.hw18.service.BookService;

import java.util.List;

@RestController
public class BookController {


    private final BookService bookService;

    public BookController(@Qualifier("simpleBookServiceWithHystrix") BookService bookService) {
        this.bookService = bookService;
    }

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

    @GetMapping(path = "/api/book/author/{id}")
    public List<Book> bookListByAuthor(@PathVariable("id") long authorId) {
        return bookService.getBookByAuthor(authorId);
    }

}
