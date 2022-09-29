package ru.otus.projs.hw04.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class TestResult {
    private final User user;
    private final List<QuestionResult> questionResults;
}
