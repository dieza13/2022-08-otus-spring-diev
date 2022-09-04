package ru.otus.projs.hw01.service.handler;

import lombok.RequiredArgsConstructor;
import ru.otus.projs.hw01.model.Question;

@RequiredArgsConstructor
public class OutputQuestionHandler implements QuestionHandler{

    static String ANSWER_OUT_FORMAT = "- %s \n";

    @Override
    public void handleQuestion(Question question) {

        System.out.println(question.getContent());
        question.getAnswers().forEach(answ -> System.out.printf(ANSWER_OUT_FORMAT, answ.getAnswerContext()));
        System.out.println();

    }

    @Override
    public void handleTitle(String title) {
        System.out.println(title);
    }

}
