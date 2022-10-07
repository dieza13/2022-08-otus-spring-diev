package ru.otus.projs.hw05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Genre {

    public Genre() {
        id = null;
        name = null;
    }

    private final Long id;
    private final String name;
}
