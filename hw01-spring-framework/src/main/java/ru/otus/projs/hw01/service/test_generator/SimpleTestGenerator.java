package ru.otus.projs.hw01.service.test_generator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.util.CollectionUtils;
import ru.otus.projs.hw01.model.Question;
import ru.otus.projs.hw01.model.QuestionResult;
import ru.otus.projs.hw01.service.handler.QuestionHandler;
import ru.otus.projs.hw01.service.handler.QuestionResultHandler;
import ru.otus.projs.hw01.service.handler.QuestionsPreHandler;
import ru.otus.projs.hw01.service.reader.QuestionReader;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SimpleTestGenerator extends TestGenerator {

    public SimpleTestGenerator(
            QuestionReader questionReader
            ,QuestionHandler questionHandler
            ,QuestionsPreHandler questionPreHandler
            ,QuestionResultHandler questionResultHandler
    ) {
        super(questionReader);
        this.questionHandler = questionHandler;
        this.questionPreHandler = questionPreHandler;
        this.questionResultHandler = questionResultHandler;
    }

    QuestionHandler questionHandler;
    QuestionsPreHandler questionPreHandler;
    QuestionResultHandler questionResultHandler;

    @Override
    protected List<Question> preGenerate(List<Question> questions) {
        return questionPreHandler.preHandleQuestions(questions);
    }

    @Override
    protected void postGenerate(List<QuestionResult> results) {
        questionResultHandler.handleResult(results);
    }

    @Override
    protected List<QuestionResult> generate(List<Question> questions) {

        if (CollectionUtils.isEmpty(questions)) {
            return null;
        }
        return questions
                .stream()
                .map(questionHandler::handleQuestion)
                .collect(Collectors.toList());

    }

}
