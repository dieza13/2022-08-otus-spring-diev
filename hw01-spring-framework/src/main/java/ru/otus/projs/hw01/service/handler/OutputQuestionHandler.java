package ru.otus.projs.hw01.service.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.model.Question;
import ru.otus.projs.hw01.model.QuestionResult;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OutputQuestionHandler implements QuestionHandler{

    static String ANSWER_OUT_FORMAT = "- %s \n";
    static String QUESTION_OUT_FORMAT = "%s \n";
    InOutStringHandler inOutStringHandler;

    @Override
    public QuestionResult<String> handleQuestion(Question question) {

        inOutStringHandler.writeString(String.format(QUESTION_OUT_FORMAT,question.getContent()));
        question.getAnswers().forEach(answ -> inOutStringHandler.writeString(String.format(ANSWER_OUT_FORMAT, answ.getAnswerContext())));
        inOutStringHandler.writeString("\n");

        return new QuestionResult<>(question, "Console output");
    }

}
