package ru.otus.projs.hw04.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SimpleResult implements QuestionResult {

    private final Question question;
    private final String answer;

    private Boolean result;

    @Override
    public boolean isSuccess() {

        if (result == null) {
            result = question.isCorrectAnswer(answer);
        }
        return result;

    }

}
