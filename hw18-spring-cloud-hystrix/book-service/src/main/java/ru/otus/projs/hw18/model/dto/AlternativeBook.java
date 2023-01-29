package ru.otus.projs.hw18.model.dto;

import lombok.Getter;
import ru.otus.projs.hw18.model.Book;

public class AlternativeBook extends Book {
    @Getter
    private String sorryMessage;
    public AlternativeBook(Book book, String sorry) {
        setId(book.getId());
        setName(book.getName());
        setAuthor(book.getAuthor());
        setGenre(book.getGenre());
        sorryMessage = sorry;
    }
}
