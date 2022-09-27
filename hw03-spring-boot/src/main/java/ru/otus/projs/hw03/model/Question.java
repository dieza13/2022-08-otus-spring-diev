package ru.otus.projs.hw03.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Data
public abstract class Question {

    private final String content;
    private final List<Answer> answers;

    public abstract Answer getCorrectAnswer();
    public abstract boolean isCorrectAnswer(String answer);

    public Optional<Answer> getAnswer(int num) {
        if (num >= 0 && num < answers.size()) {
            return Optional.of(answers.get(num));
        }
        return Optional.empty();
    }

}
