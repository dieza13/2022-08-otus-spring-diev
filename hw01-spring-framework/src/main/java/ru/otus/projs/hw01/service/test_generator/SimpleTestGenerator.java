package ru.otus.projs.hw01.service.test_generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.service.reader.QuestionReader;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SimpleTestGenerator implements TestGenerator {

    static String TITLE = "-----Hello student! Lets take a test!----- \n";

    QuestionReader questionReader;

    @Override
    public void generateTest() {

        System.out.println(TITLE);
        questionReader.getQuestionList().forEach(System.out::println);

    }
}
