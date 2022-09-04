package ru.otus.projs.hw01.service.test_generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.service.handler.QuestionHandler;
import ru.otus.projs.hw01.service.reader.QuestionReader;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SimpleTestGenerator implements TestGenerator {

    QuestionReader questionReader;
    QuestionHandler questionHandler;
    String testTitle;

    @Override
    public void generateTest() {

        questionHandler.handleTitle(testTitle);
        questionReader.getQuestionList().forEach(questionHandler::handleQuestion);

    }
}
