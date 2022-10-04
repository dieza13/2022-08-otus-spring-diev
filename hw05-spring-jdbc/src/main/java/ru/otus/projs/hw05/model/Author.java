package ru.otus.projs.hw05.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Author {

    private final Long id;
    private final String name;
    private final String lastName;

    public Author() {
        id = null;
        name = null;
        lastName = null;
    }

}
