package ru.otus.projs.hw02.exception;

public class QuestionNoAnswerException extends RuntimeException{

    private final String questionContent;

    public QuestionNoAnswerException(String questionContent) {
        super("No answer found for question:\"" + questionContent + "\"");
        this.questionContent = questionContent;
    }

    public QuestionNoAnswerException(String questionContent, Throwable cause) {
        super("No answer found for question:\"" + questionContent + "\"", cause);
        this.questionContent = questionContent;
    }

    public String getQuestionContent() {
        return questionContent;
    }
}
