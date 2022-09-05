package ru.otus.projs.hw01.service.handler;

import ru.otus.projs.hw01.model.Question;
import ru.otus.projs.hw01.model.QuestionResult;

public interface QuestionHandler {

    QuestionResult handleQuestion(Question question);

}
