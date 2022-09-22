package ru.otus.projs.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SimpleInOutServiceTest {

    private SimpleInOutService inOutService;
    private MessageService messageService;
    private PrintStream printStream;
    private InputStream inputStream;

    @BeforeEach
    void setUp() {
        messageService = Mockito.mock(SimpleMessageService.class);
        printStream = Mockito.mock(PrintStream.class);
        inputStream = Mockito.mock(InputStream.class);
        inOutService = new SimpleInOutService(messageService, inputStream, printStream);
    }

    @Test
    void writeString() {

        doAnswer(a -> "test").when(printStream).print("test");

        inOutService.writeString("test");

        verify(printStream, times(1)).print(anyString());
    }
}