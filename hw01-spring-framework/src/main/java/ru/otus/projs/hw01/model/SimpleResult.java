package ru.otus.projs.hw01.model;

public class SimpleResult implements TestResult{
    @Override
    public void applyQuestionAnswer(String answer, Question question) {
        // пока результат не нужен
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
