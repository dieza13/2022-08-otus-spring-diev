package ru.otus.projs.hw02.service.test_executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw02.exception.HandleTestResultException;
import ru.otus.projs.hw02.service.*;
import ru.otus.projs.hw02.service.reader.QuestionService;
import ru.otus.projs.hw02.service.reader.SimpleQuestionService;
import ru.otus.projs.hw02.service.user.SimpleUserService;
import ru.otus.projs.hw02.service.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimpleTestExecutorTest {

    private SimpleTestExecutor simpleTestExecutor;
    private QuestionResultService questionResultService;
    private QuestionService questionService;
    private UserService userService;
    private TestingService testingService;
    private ResultPrinter resultPrinterService;
    private MessageWriterService messageWriterService;

    @BeforeEach
    void setUp() {
        questionResultService = Mockito.mock(SimpleQuestionResultService.class);
        questionService = Mockito.mock(SimpleQuestionService.class);
        userService = Mockito.mock(SimpleUserService.class);
        testingService = Mockito.mock(SimpleTestingService.class);
        resultPrinterService = Mockito.mock(SimpleResultPrinter.class);
        messageWriterService = Mockito.mock(MessageWriterService.class);

        simpleTestExecutor = new SimpleTestExecutor(questionService, userService, testingService, questionResultService, resultPrinterService, messageWriterService);
    }

    @Test
    void execute() {

        simpleTestExecutor.execute();
        verify(questionService, times(1)).findAll();
        verify(userService, times(1)).askUserInfo();
        verify(testingService, times(1)).askQuestions(any(), any());
        verify(questionResultService, times(1)).handleResult(any());
        verify(resultPrinterService, times(1)).print(any());

    }

    @Test
    void execute_withError() {
        when(questionResultService.handleResult(any())).thenThrow(new HandleTestResultException(null));

        simpleTestExecutor.execute();
        verify(questionService, times(1)).findAll();
        verify(userService, times(1)).askUserInfo();
        verify(testingService, times(1)).askQuestions(any(), any());
        verify(questionResultService, times(1)).handleResult(any());
        verify(resultPrinterService, times(0)).print(any());
        verify(messageWriterService, times(1)).writeStringFromSource("err.main.handle", "Error on handling test result");

    }
}