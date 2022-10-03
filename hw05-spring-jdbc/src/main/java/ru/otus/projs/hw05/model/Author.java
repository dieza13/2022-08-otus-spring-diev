package ru.otus.projs.hw05.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Author {

    public Author() {
        id = null;
        name = null;
        lastName = null;
    }

    private final Long id;
    private final String name;
    private final String lastName;
}
