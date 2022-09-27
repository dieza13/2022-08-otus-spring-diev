package ru.otus.projs.hw03.factory;

import ru.otus.projs.hw03.model.Answer;
import ru.otus.projs.hw03.model.Question;

import java.util.List;

public interface QuestionFactory {

    Question createQuestion(String content, List<Answer> answers);

}
