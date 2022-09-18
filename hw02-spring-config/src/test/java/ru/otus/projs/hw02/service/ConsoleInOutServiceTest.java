package ru.otus.projs.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConsoleInOutServiceTest {

    private ConsoleInOutService inOutService;
    private MessageService messageService;
    private PrintStream printStream;
    private InputStream inputStream;

    @BeforeEach
    void setUp() {
        messageService = Mockito.mock(SimpleMessageService.class);
        printStream = Mockito.mock(PrintStream.class);
        inputStream = Mockito.mock(InputStream.class);
        inOutService = new ConsoleInOutService(messageService, inputStream, printStream);
    }

    @Test
    void writeString() {

        doAnswer(a -> "test").when(printStream).print("test");

        inOutService.writeString("test");

        verify(printStream, times(1)).print(anyString());
    }

    @Test
    void writeStringFromSourceOnlyCode() {

        doAnswer(a -> "test").when(messageService).getMessage("test");
        doAnswer(a -> "test").when(printStream).print("test");
        inOutService.writeStringFromSource("test");
        verify(printStream, times(1)).print(anyString());
        verify(messageService, times(1)).getMessage(anyString());
    }

    @Test
    void testWriteStringFromSourceCodeWithArgs() {

        doAnswer(a -> "test").when(messageService).getMessage("test", new String[]{"a"});
        doAnswer(a -> "test").when(printStream).print("test");
        inOutService.writeStringFromSource("test", new String[]{"a"});
        verify(printStream, times(1)).print(anyString());
        verify(messageService, times(1)).getMessage(anyString(), any());
    }
}