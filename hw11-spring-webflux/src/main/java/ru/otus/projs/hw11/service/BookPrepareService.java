package ru.otus.projs.hw11.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw11.model.Book;

@Service
public class BookPrepareService implements BookService {
    @Override
    public Book prepareBook4Save(Book book) {
        book.setId(book.getId().equals("") ? null : book.getId());
        return book;
    }
}
