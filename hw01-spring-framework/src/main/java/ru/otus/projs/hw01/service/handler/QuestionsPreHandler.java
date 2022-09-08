package ru.otus.projs.hw01.service.handler;


import ru.otus.projs.hw01.model.Question;

import java.util.List;

public interface QuestionsPreHandler {

    List<Question> preHandleQuestions(List<Question> questions);

}
