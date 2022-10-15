package hw04;


import ru.otus.projs.hw04.model.Answer;
import ru.otus.projs.hw04.model.MultiAnswerQuestion;
import ru.otus.projs.hw04.model.OneAnswerQuestion;
import ru.otus.projs.hw04.model.Question;

import java.util.Arrays;
import java.util.List;

public class Util {

    public static Question createMultiAnswerQuestion(boolean withAnswers) {
        List<Answer> answers = withAnswers ? Arrays.asList(
                new Answer("John", true),
                new Answer("Bob", false)
        ) : null;
        return new MultiAnswerQuestion("Who are you?",answers);
    }

    public static Question createOneAnswerQuestion(boolean withAnswers) {
        return new OneAnswerQuestion(
                "Where are you?",
                new Answer("Here", true)
        );
    }

}