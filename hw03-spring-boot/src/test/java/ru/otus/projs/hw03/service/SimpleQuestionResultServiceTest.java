package ru.otus.projs.hw03.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.projs.hw03.Util;
import ru.otus.projs.hw03.config.AppParamsConfig;
import ru.otus.projs.hw03.model.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {SimpleQuestionResultService.class, AppParamsConfig.class, MessageService.class})
class SimpleQuestionResultServiceTest {

    @Autowired
    private SimpleQuestionResultService questionResultService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private AppParamsConfig props;

    @BeforeEach
    void setUp() {

        when(messageService.getMessage("text.title.student.test.result")).thenReturn("val1");
        when(messageService.getMessage("text.title.student.test.noResults")).thenReturn("val2");
        when(messageService.getMessage("text.title.student.test.result.output",2, 2)).thenReturn("val3");
        when(messageService.getMessage("text.title.student.test.passed")).thenReturn("val4");
        when(messageService.getMessage("text.title.student.test.notPassed")).thenReturn("val5");
    }

    @Test
    void handleResult_OK() {
        when(props.getPassLimit()).thenReturn(2);
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