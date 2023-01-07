package ru.otus.projs.hw15.exception;

public class RuleNotFound extends RuntimeException{
    public RuleNotFound() {
        super("not found");
    }
}
