package ru.otus.projs.hw02.service.handler;

import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.TestResult;

public interface QuestionHandler {

    TestResult handleQuestion(Question question);

}
