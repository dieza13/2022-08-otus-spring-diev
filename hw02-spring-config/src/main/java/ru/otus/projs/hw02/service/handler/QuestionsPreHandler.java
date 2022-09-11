package ru.otus.projs.hw02.service.handler;


import ru.otus.projs.hw02.model.Question;

import java.util.List;

public interface QuestionsPreHandler {

    List<Question> preHandleQuestions(List<Question> questions);

}
