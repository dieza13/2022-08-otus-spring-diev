package ru.otus.projs.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.projs.hw12.model.Author;
import ru.otus.projs.hw12.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(path = "/api/author")
    public List<Author> authorList() {
        return authorService.findAll();
    }

    @GetMapping(path = "/api/author/{id}")
    public Author getAuthor(@PathVariable("id") long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping(path = "/api/author")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author);
        author.setId(savedAuthor.getId());
        return ResponseEntity.ok(author);
    }

    @DeleteMapping(path = "/api/author/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok().build();
    }

}
