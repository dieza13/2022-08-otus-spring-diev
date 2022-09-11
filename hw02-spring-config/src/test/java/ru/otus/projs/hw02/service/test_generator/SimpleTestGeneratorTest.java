package ru.otus.projs.hw02.service.test_generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.projs.hw02.model.Answer;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.service.handler.*;
import ru.otus.projs.hw02.service.reader.CSVQuestionReader;
import ru.otus.projs.hw02.service.reader.QuestionReader;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =
        {SimpleTestGenerator.class,
                ApplyResultQuestionHandler.class,
                StudentGreetingPreHandler.class,
                StudentTestQuestionResultHandler.class,
                ConsoleInOutStringHandler.class,
                CSVQuestionReader.class})
@TestPropertySource("classpath:test-application.properties")
class SimpleTestGeneratorTest {

    @Autowired
    private SimpleTestGenerator testGenerator;
    private QuestionHandler questionHandler;
    private QuestionsPreHandler questionPreHandler;
    private QuestionResultHandler questionResultHandler;
    private QuestionReader questionReader;

    @BeforeEach
    void setUp() {
        questionHandler = Mockito.mock(ApplyResultQuestionHandler.class);
        questionPreHandler = Mockito.mock(StudentGreetingPreHandler.class);
        questionResultHandler = Mockito.mock(StudentTestQuestionResultHandler.class);
        questionReader = Mockito.mock(CSVQuestionReader.class);
        ReflectionTestUtils.setField(testGenerator, "questionReader", questionReader);
        ReflectionTestUtils.setField(testGenerator, "questionHandler", questionHandler);
        ReflectionTestUtils.setField(testGenerator, "questionPreHandler", questionPreHandler);
        ReflectionTestUtils.setField(testGenerator, "questionResultHandler", questionResultHandler);
    }

    @Test
    void generateTest() {

        List<Question> questions = Arrays.asList(createQuestion(1),createQuestion(2));
        when(questionReader.getQuestionList()).thenAnswer(a->questions);
        when(questionPreHandler.preHandleQuestions(any())).thenAnswer(a->questions);
        doNothing().when(questionResultHandler).handleResult(any());

        testGenerator.generateTest();

        verify(questionReader,times(1)).getQuestionList();
        verify(questionPreHandler,times(1)).preHandleQuestions(any());
        verify(questionResultHandler,times(1)).handleResult(any());
    }

    private Question createQuestion(int i) {
        List<Answer> answers = Arrays.asList(
                new Answer("John" + i, true),
                new Answer("Bob" + i, false)
        );
        return new Question("Who are you?",answers);
    }
}