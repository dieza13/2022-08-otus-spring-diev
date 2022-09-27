package ru.otus.projs.hw03.model;

import lombok.Getter;

import java.util.List;

@Getter
public class OneAnswerQuestion extends Question {

    public OneAnswerQuestion(String content, Answer answer) {
        super(content, List.of(answer));
    }

    @Override
    public Answer getCorrectAnswer() {
        return getAnswers().get(0);
    }

    @Override
    public boolean isCorrectAnswer(String answer) {
        return answer
                .toLowerCase()
                .trim()
                .equals(
                        getAnswers()
                                .get(0)
                                .getAnswerContext()
                                .trim()
                                .toLowerCase()
                );
    }
}

