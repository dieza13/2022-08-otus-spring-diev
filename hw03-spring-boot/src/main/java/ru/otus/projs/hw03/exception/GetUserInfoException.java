package ru.otus.projs.hw03.exception;

public class GetUserInfoException extends RuntimeException{

    public GetUserInfoException() {
        super("Error with getting user info");
    }

    public GetUserInfoException(Throwable cause) {
        super("Error with getting user info", cause);
    }

}
