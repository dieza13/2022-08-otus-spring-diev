package ru.otus.projs.hw02.service.test_executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw02.exception.ETestException;
import ru.otus.projs.hw02.service.*;
import ru.otus.projs.hw02.service.reader.QuestionService;
import ru.otus.projs.hw02.service.reader.SimpleQuestionService;
import ru.otus.projs.hw02.service.user.SimpleUserService;
import ru.otus.projs.hw02.service.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimpleTestExecutorTest {

    private SimpleTestExecutor simpleTestExecutor;
    private QuestionResultService questionResultService;
    private QuestionService questionService;
    private UserService userService;
    private TestingService testingService;
    private ResultPrinter resultPrinterService;
    private InOutService inOutService;

    @BeforeEach
    void setUp() {
        questionResultService = Mockito.mock(SimpleQuestionResultService.class);
        questionService = Mockito.mock(SimpleQuestionService.class);
        userService = Mockito.mock(SimpleUserService.class);
        testingService = Mockito.mock(SimpleTestingService.class);
        resultPrinterService = Mockito.mock(SimpleResultPrinter.class);
        inOutService = Mockito.mock(ConsoleInOutService.class);

        simpleTestExecutor = new SimpleTestExecutor(questionService, userService, testingService, questionResultService, resultPrinterService, inOutService);
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
        when(questionResultService.handleResult(any())).thenThrow(new ETestException("OPS"));

        simpleTestExecutor.execute();
        verify(questionService, times(1)).findAll();
        verify(userService, times(1)).askUserInfo();
        verify(testingService, times(1)).askQuestions(any(), any());
        verify(questionResultService, times(1)).handleResult(any());
        verify(resultPrinterService, times(0)).print(any());
        verify(inOutService, times(1)).writeStringFromSource("err.main.handle", new String[]{"OPS"});

    }
}