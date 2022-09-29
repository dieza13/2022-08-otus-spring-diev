package ru.otus.projs.hw04.factory;

import ru.otus.projs.hw04.model.Answer;
import ru.otus.projs.hw04.model.Question;

import java.util.List;

public interface QuestionFactory {

    Question createQuestion(String content, List<Answer> answers);

}
