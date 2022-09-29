package ru.otus.projs.hw04.exception;

import ru.otus.projs.hw04.model.Question;

public class OnAskQuestionException extends RuntimeException{

    private final Question question;

    public OnAskQuestionException(Question question) {
        super("Error on asking question: \"" + question.getContent() + "\"");
        this.question = question;
    }

    public OnAskQuestionException(Question question, Throwable cause) {
        super("Error on asking question: \"" + question.getContent() + "\"", cause);
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
