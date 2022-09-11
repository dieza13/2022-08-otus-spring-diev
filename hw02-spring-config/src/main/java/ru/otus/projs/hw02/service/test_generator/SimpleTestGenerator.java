package ru.otus.projs.hw02.service.test_generator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.TestResult;
import ru.otus.projs.hw02.service.handler.QuestionHandler;
import ru.otus.projs.hw02.service.handler.QuestionResultHandler;
import ru.otus.projs.hw02.service.handler.QuestionsPreHandler;
import ru.otus.projs.hw02.service.reader.QuestionReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SimpleTestGenerator implements TestGenerator {

    QuestionHandler questionHandler;
    QuestionsPreHandler questionPreHandler;
    QuestionResultHandler questionResultHandler;
    QuestionReader questionReader;

    public SimpleTestGenerator(
            QuestionReader questionReader
            ,QuestionHandler questionHandler
            ,QuestionsPreHandler questionPreHandler
            ,QuestionResultHandler questionResultHandler
    ) {
        this.questionReader = questionReader;
        this.questionHandler = questionHandler;
        this.questionPreHandler = questionPreHandler;
        this.questionResultHandler = questionResultHandler;
    }

    public void generateTest() {

        List<Question> questions = questionReader.getQuestionList();
        List<Question> modifiedQuestions = preGenerate(questions);
        List<TestResult> results = generate(
                CollectionUtils.isEmpty(modifiedQuestions) ? questions : modifiedQuestions
        );
        postGenerate(results);

    }

    private List<Question> preGenerate(List<Question> questions) {
        return questionPreHandler.preHandleQuestions(questions);
    }

    private void postGenerate(List<TestResult> results) {
        questionResultHandler.handleResult(results);
    }

    private List<TestResult> generate(List<Question> questions) {

        if (CollectionUtils.isEmpty(questions)) {
            return null;
        }
        return questions
                .stream()
                .map(questionHandler::handleQuestion)
                .collect(Collectors.toList());

    }


}
