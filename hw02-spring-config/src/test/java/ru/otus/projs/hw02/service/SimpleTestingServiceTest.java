package ru.otus.projs.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw02.Util;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.SimpleResult;
import ru.otus.projs.hw02.model.TestResult;
import ru.otus.projs.hw02.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class SimpleTestingServiceTest {

    private SimpleTestingService testingService;
    private MessageService messageService;
    private SimpleInOutService inOutService;
    private MessageWriterService messageWriterService;

    @BeforeEach
    void setUp() {
        messageService = Mockito.mock(SimpleMessageService.class);
        inOutService = Mockito.mock(SimpleInOutService.class);
        messageWriterService = Mockito.mock(MessageWriterService.class);
        testingService = new SimpleTestingService(inOutService,messageWriterService,messageService);
    }

    @Test
    void askQuestions() {
        User user = new User("a","b");
        List<Question> questions = List.of(Util.createMultiAnswerQuestion(true));
        when(inOutService.readString()).thenReturn("1");
        doNothing().when(inOutService).writeString(any());
        TestResult testResult = testingService.askQuestions(user,questions);
        assertEquals(user, testResult.getUser());
        SimpleResult result = (SimpleResult)testResult.getQuestionResults().get(0);
        assertEquals(questions.get(0), result.getQuestion());
        assertEquals("1",result.getAnswer());
    }

}