package ru.otus.projs.hw02.service.handler;

import org.junit.jupiter.api.Assertions;
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
import ru.otus.projs.hw02.model.SimpleResult;
import ru.otus.projs.hw02.model.TestResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplyResultQuestionHandler.class, ConsoleInOutStringHandler.class})
@TestPropertySource("classpath:test-application.properties")
class ApplyResultQuestionHandlerTest {

    @Autowired
    private ApplyResultQuestionHandler questionHandler;
    private InOutStringHandler inOutStringHandler;

    @BeforeEach
    void setUp() {
        inOutStringHandler = Mockito.mock(ConsoleInOutStringHandler.class);
        ReflectionTestUtils.setField(questionHandler, "inOutStringHandler", inOutStringHandler);
    }

    @Test
    void handleQuestion_2Answers() {
        ApplyResultQuestionHandler handler = Mockito.spy(questionHandler);
        doAnswer(a -> new SimpleResult(null, "cucumber")).when(handler).handleResult(any());
        Question question = createQuestion(true);

        TestResult testResult = handler.handleQuestion(question);

        verify(inOutStringHandler, times(3)).writeString(any());
        verify(inOutStringHandler, times(1)).writeString("Who are you? \n");
        verify(inOutStringHandler, times(1)).writeString("1. John \n");
        verify(inOutStringHandler, times(1)).writeString("2. Bob \n");
        verify(handler, times(1)).handleResult(any());

    }

    @Test
    void handleQuestion_noAnswers() {
        ApplyResultQuestionHandler handler = Mockito.spy(questionHandler);
        doAnswer(a -> new SimpleResult(null, "cucumber")).when(handler).handleResult(any());
        Question question = createQuestion(false);

        TestResult testResult = handler.handleQuestion(question);

        verify(inOutStringHandler, times(1)).writeString(any());
        verify(handler, times(1)).handleResult(any());

    }

    @Test
    void handleResult_OK() {
        Question question = createQuestion(true);
        when(inOutStringHandler.readString()).thenAnswer(a -> "cucumber");

        SimpleResult result = (SimpleResult) questionHandler.handleResult(question);

        Assertions.assertEquals(result.getQuestion(), question);
        Assertions.assertEquals(result.getAnswer(), "cucumber");
        Assertions.assertEquals(result.getQuestion(), question);

        verify(inOutStringHandler, times(2)).writeString(any());
        verify(inOutStringHandler, times(1)).writeString("Choose answer and type its number:");
        verify(inOutStringHandler, times(1)).readString();
    }


    @Test
    void handleResult_successfulAnswer() {
        Question question = createQuestion(true);
        when(inOutStringHandler.readString()).thenAnswer(a -> "1");

        SimpleResult result = (SimpleResult) questionHandler.handleResult(question);
//
        assertTrue(result.isSuccess());
    }


    @Test
    void handleResult_unsuccessfulAnswer() {
        Question question = createQuestion(true);
        when(inOutStringHandler.readString()).thenAnswer(a -> "2");

        SimpleResult result = (SimpleResult) questionHandler.handleResult(question);
//
        assertFalse(result.isSuccess());
    }

    private Question createQuestion(boolean withAnswers) {
        List<Answer> answers = withAnswers ? Arrays.asList(
                new Answer("John", true),
                new Answer("Bob", false)
        ) : null;
        return new Question("Who are you?",answers);
    }
}