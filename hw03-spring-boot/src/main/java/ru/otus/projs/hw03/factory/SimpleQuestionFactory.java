package ru.otus.projs.hw03.factory;

import org.springframework.stereotype.Component;
import ru.otus.projs.hw03.model.Answer;
import ru.otus.projs.hw03.model.MultiAnswerQuestion;
import ru.otus.projs.hw03.model.OneAnswerQuestion;
import ru.otus.projs.hw03.model.Question;

import java.util.List;

@Component
public class SimpleQuestionFactory implements QuestionFactory{

    public Question createQuestion(String content, List<Answer> answers) {
        if (answers.size() > 1) {
            return new MultiAnswerQuestion(content,answers);
        } else if (answers.size() == 1) {
            return new OneAnswerQuestion(content, answers.get(0));
        }
        return null;
    }
}
