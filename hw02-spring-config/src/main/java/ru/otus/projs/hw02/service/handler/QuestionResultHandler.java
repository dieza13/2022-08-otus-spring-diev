package ru.otus.projs.hw02.service.handler;

import ru.otus.projs.hw02.model.TestResult;

import java.util.List;

public interface QuestionResultHandler {
    void handleResult(List<TestResult> questionResults);
}
