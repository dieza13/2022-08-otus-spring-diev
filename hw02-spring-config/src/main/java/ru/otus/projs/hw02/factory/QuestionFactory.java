package ru.otus.projs.hw02.factory;

import ru.otus.projs.hw02.model.Answer;
import ru.otus.projs.hw02.model.Question;

import java.util.List;

public interface QuestionFactory {

    Question createQuestion(String content, List<Answer> answers);

}
