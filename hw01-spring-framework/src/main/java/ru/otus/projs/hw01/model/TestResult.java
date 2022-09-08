package ru.otus.projs.hw01.model;

public interface TestResult {

    void applyQuestionAnswer(String answer, Question question);
    boolean isSuccess();

}
