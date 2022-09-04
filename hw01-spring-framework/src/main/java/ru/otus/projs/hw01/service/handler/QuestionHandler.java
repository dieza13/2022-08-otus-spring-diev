package ru.otus.projs.hw01.service.handler;

import ru.otus.projs.hw01.model.Question;

public interface QuestionHandler {

    void handleQuestion(Question question);

    void handleTitle(String title);

}
