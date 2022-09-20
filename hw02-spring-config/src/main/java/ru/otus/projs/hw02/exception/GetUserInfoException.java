package ru.otus.projs.hw02.exception;

public class GetUserInfoException extends RuntimeException{

    public GetUserInfoException() {
        super("Error with getting user info");
    }

    public GetUserInfoException(String resourceName, Throwable cause) {
        super("Error with getting user info", cause);
    }

}
