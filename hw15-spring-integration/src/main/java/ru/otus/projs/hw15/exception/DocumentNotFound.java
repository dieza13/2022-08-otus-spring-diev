package ru.otus.projs.hw15.exception;

public class DocumentNotFound extends RuntimeException{
    public DocumentNotFound() {
        super("not found");
    }
}
