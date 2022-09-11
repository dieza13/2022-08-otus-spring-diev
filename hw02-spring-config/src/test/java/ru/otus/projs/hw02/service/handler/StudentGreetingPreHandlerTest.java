package ru.otus.projs.hw02.service.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StudentGreetingPreHandler.class, ConsoleInOutStringHandler.class})
@TestPropertySource("classpath:test-application.properties")
class StudentGreetingPreHandlerTest {

    @Autowired
    private QuestionsPreHandler resultHandler;

    @Test
    void preHandleQuestions_OK() {
        InOutStringHandler inOutStringHandler = Mockito.mock(ConsoleInOutStringHandler.class);
        ReflectionTestUtils.setField(resultHandler, "inOutStringHandler", inOutStringHandler);
        doNothing().when(inOutStringHandler).writeString(any());

        resultHandler.preHandleQuestions(null);

        verify(inOutStringHandler, times(2)).writeString(any());
        verify(inOutStringHandler, times(1)).writeString("-----Hello student! Lets take a test!----- \n");
        verify(inOutStringHandler, times(1)).writeString("Enter your name, please! \n");
        verify(inOutStringHandler, times(1)).readString();

    }

}