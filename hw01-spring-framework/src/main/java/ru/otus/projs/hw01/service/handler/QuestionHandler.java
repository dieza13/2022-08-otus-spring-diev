package ru.otus.projs.hw01.service.handler;

import ru.otus.projs.hw01.model.Question;
import ru.otus.projs.hw01.model.TestResult;

public interface QuestionHandler {

    TestResult handleQuestion(Question question);

}
