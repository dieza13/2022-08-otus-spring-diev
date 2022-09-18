package ru.otus.projs.hw02.exception;

public class ETestException extends RuntimeException{

    public ETestException(String message) {
        super(message);
    }

    public ETestException(String message, Throwable cause) {
        super(message, cause);
    }
}
