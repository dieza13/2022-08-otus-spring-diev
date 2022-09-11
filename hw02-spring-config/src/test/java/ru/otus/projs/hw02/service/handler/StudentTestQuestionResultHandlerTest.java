package ru.otus.projs.hw02.service.handler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.projs.hw02.model.TestResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StudentTestQuestionResultHandler.class, ConsoleInOutStringHandler.class})
@TestPropertySource("classpath:test-application.properties")
class StudentTestQuestionResultHandlerTest {

    @Autowired
    private QuestionResultHandler resultHandler;
    private InOutStringHandler inOutStringHandler;

    @BeforeEach
    void setUp() {
        inOutStringHandler = Mockito.mock(ConsoleInOutStringHandler.class);
        ReflectionTestUtils.setField(resultHandler, "inOutStringHandler", inOutStringHandler);
    }

    @Test
    void handleResult_OK() {

        doNothing().when(inOutStringHandler).writeString(any());
        TestResult res = Mockito.mock(TestResult.class);
        when(res.isSuccess()).thenAnswer(a-> Boolean.TRUE);
        List<TestResult> results = Arrays.asList(res, res);

        resultHandler.handleResult(results);

        verify(res, times(2)).isSuccess();
        verify(inOutStringHandler, times(4)).writeString(any());
        verify(inOutStringHandler, times(1)).writeString("Student test result: \n");
        verify(inOutStringHandler, times(1)).writeString("Question count: 2; Correct answer count: 2\n");
        verify(inOutStringHandler, times(1)).writeString("Test not passed\n");
        verify(inOutStringHandler, times(1)).writeString("-----Test finished!-----");

    }

    @Test
    void handleResult_withOneBadResult() {
        doNothing().when(inOutStringHandler).writeString(any());
        TestResult res = Mockito.mock(TestResult.class);
        when(res.isSuccess()).thenAnswer(a-> Boolean.TRUE);
        TestResult res_bad = Mockito.mock(TestResult.class);
        when(res_bad.isSuccess()).thenAnswer(a-> Boolean.FALSE);
        List<TestResult> results2 = Arrays.asList(res, res_bad);

        resultHandler.handleResult(results2);

        verify(inOutStringHandler, times(1)).writeString("Question count: 2; Correct answer count: 1\n");
    }

    @Test
    void handleResult_noResults() {
        doNothing().when(inOutStringHandler).writeString(any());
        TestResult res = Mockito.mock(TestResult.class);
        when(res.isSuccess()).thenAnswer(a-> Boolean.TRUE);

        resultHandler.handleResult(null);

        verify(res, times(0)).isSuccess();
    }
}