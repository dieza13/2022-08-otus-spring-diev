package ru.otus.projs.hw02.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.SimpleResult;
import ru.otus.projs.hw02.model.TestResult;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplyResultQuestionHandler implements QuestionHandler{

    static String ANSWER_OUT_FORMAT = "%d. %s \n";
    static String QUESTION_OUT_FORMAT = "%s \n";

    String answerOneTitle;
    String answerVariantsTitle;

    InOutStringHandler inOutStringHandler;

    public ApplyResultQuestionHandler(
            InOutStringHandler inOutStringHandler,
            @Value("${text.title.answer.one}") String answerOneTitle,
            @Value("${text.title.answer.variants}") String answerVariantsTitle
    ) {
        this.inOutStringHandler = inOutStringHandler;
        this.answerOneTitle = answerOneTitle;
        this.answerVariantsTitle = answerVariantsTitle;
    }

    @Override
    public TestResult handleQuestion(Question question) {

        inOutStringHandler.writeString(String.format(QUESTION_OUT_FORMAT,question.getContent()));
        if (question.getAnswers() != null && question.getAnswers().size() > 1)
            for (int i = 0; i < question.getAnswers().size(); i++) {
                if (question.getAnswers().size() > 1)
                    inOutStringHandler.writeString(String.format(ANSWER_OUT_FORMAT, i + 1, question.getAnswer(i).getAnswerContext()));
            }
        return handleResult(question);

    }

    protected TestResult handleResult(Question question) {

        String answerTitle = (question.getAnswers() != null && question.getAnswers().size() > 1) ?
                answerVariantsTitle:
                answerOneTitle;
        inOutStringHandler.writeString(answerTitle);
        String typedAnswer = inOutStringHandler.readString();
        inOutStringHandler.writeString("\n");
        return new SimpleResult(question, typedAnswer);

    }

}
