package ru.otus.projs.hw04.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Answer {

    private final String answerContext;
    private final boolean isCorrect;

}
