package ru.otus.projs.hw03.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.projs.hw03.Util;
import ru.otus.projs.hw03.model.Question;
import ru.otus.projs.hw03.model.SimpleResult;
import ru.otus.projs.hw03.model.TestResult;
import ru.otus.projs.hw03.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {SimpleTestingService.class, SimpleInOutService.class, MessageService.class,MessageWriterService.class})
class SimpleTestingServiceTest {

    @Autowired
    private SimpleTestingService testingService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private SimpleInOutService inOutService;
    @MockBean
    private MessageWriterService messageWriterService;


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