package ru.otus.projs.hw05.model;

import lombok.Data;

@Data
public class Book {
    private final Long id;
    private final String name;
    private Author author;
    private Genre genre;

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book() {
        id = null;
        name = null;
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
