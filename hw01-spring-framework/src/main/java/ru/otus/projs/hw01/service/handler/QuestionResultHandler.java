package ru.otus.projs.hw01.service.handler;

import ru.otus.projs.hw01.model.TestResult;

import java.util.List;

public interface QuestionResultHandler {
    void handleResult(List<TestResult> questionResults);
}
