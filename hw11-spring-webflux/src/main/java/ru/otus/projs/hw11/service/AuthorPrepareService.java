package ru.otus.projs.hw11.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw11.model.Author;

@Service
public class AuthorPrepareService implements AuthorService {

    @Override
    public Author prepareAuthor4Save(Author author) {
        author.setId(author.getId().equals("") ? null : author.getId());
        return author;
    }

}
