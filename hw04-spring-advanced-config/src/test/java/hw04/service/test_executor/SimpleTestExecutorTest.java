package hw04.service.test_executor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw04.exception.HandleTestResultException;
import ru.otus.projs.hw04.service.MessageWriterService;
import ru.otus.projs.hw04.service.QuestionResultService;
import ru.otus.projs.hw04.service.ResultPrinter;
import ru.otus.projs.hw04.service.TestingService;
import ru.otus.projs.hw04.service.reader.QuestionService;
import ru.otus.projs.hw04.service.test_executor.SimpleTestExecutor;
import ru.otus.projs.hw04.service.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SimpleTestExecutor.class,QuestionResultService.class,QuestionService.class,UserService.class,
        TestingService.class,ResultPrinter.class,MessageWriterService.class})
class SimpleTestExecutorTest {

    @Autowired
    private SimpleTestExecutor simpleTestExecutor;
    @MockBean
    private QuestionResultService questionResultService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private UserService userService;
    @MockBean
    private TestingService testingService;
    @MockBean
    private ResultPrinter resultPrinterService;
    @MockBean
    private MessageWriterService messageWriterService;


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