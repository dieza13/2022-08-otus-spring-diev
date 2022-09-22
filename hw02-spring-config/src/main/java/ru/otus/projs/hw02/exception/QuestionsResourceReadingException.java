package ru.otus.projs.hw02.exception;

public class QuestionsResourceReadingException extends RuntimeException{

    private final String resourceName;

    public QuestionsResourceReadingException(String resourceName) {
        super("No resource found with name " + resourceName);
        this.resourceName = resourceName;
    }

    public QuestionsResourceReadingException(String resourceName, Throwable cause) {
        super("No resource found with name " + resourceName, cause);
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

}
