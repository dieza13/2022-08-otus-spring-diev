package ru.otus.projs.hw15.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum DocumentType {
    passport(1, "паспорт"),
    driver_license(2, "права");

    DocumentType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private final int id;
    private final String name;

    public static Optional<DocumentType> get(String name) {
        return Stream.of(passport, driver_license)
                .filter(dt -> dt.name.equals(name)).findFirst();
    }
}
