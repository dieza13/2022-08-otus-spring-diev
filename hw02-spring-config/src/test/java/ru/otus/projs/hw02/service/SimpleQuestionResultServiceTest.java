package ru.otus.projs.hw02.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw02.Util;
import ru.otus.projs.hw02.model.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimpleQuestionResultServiceTest {

    private SimpleQuestionResultService questionResultService;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = Mockito.mock(SimpleMessageService.class);
        questionResultService = new SimpleQuestionResultService(messageService,2);

        when(messageService.getMessage("text.title.student.test.result")).thenReturn("val1");
        when(messageService.getMessage("text.title.student.test.noResults")).thenReturn("val2");
        when(messageService.getMessage("text.title.student.test.result.output",2, 2)).thenReturn("val3");
        when(messageService.getMessage("text.title.student.test.passed")).thenReturn("val4");
        when(messageService.getMessage("text.title.student.test.notPassed")).thenReturn("val5");
    }

    @Test
    void handleResult_OK() {
        Question question1 = Util.createMultiAnswerQuestion(true);
        Question question2 = Util.createOneAnswerQuestion(true);
        SimpleResult result1 = new SimpleResult(question1, "1");
        SimpleResult result2 = new SimpleResult(question2, question2.getCorrectAnswer().getAnswerContext());
        List<QuestionResult> results = Arrays.asList(result1,result2);
        TestResult testResult = new TestResult(new User("a","b"),results);

        String result = questionResultService.handleResult(testResult);

        Assertions.assertEquals(result, "val1val3val4");

        verify(messageService, times(2)).getMessage(any());
        verify(messageService, times(1)).getMessage(any(),anyInt(),anyInt());
    }

    @Test
    void handleResult_NoResults() {

        TestResult testResult = new TestResult(new User("a","b"),null);
        String result = questionResultService.handleResult(testResult);
        Assertions.assertEquals(result, "val2");
        verify(messageService, times(2)).getMessage(any());
    }


}