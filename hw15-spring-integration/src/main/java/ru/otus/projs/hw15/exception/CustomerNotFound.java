package ru.otus.projs.hw15.exception;

public class CustomerNotFound extends RuntimeException{
    public CustomerNotFound() {
        super("not found");
    }
}
