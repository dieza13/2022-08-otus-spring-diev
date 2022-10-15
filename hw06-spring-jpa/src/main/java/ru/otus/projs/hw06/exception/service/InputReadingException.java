package ru.otus.projs.hw06.exception.service;

public class InputReadingException extends RuntimeException{

    public InputReadingException() {
        super("Input reading exception");
    }

    public InputReadingException(Throwable cause) {
        super("Input reading exception", cause);
    }

}
