package ru.otus.projs.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ResourceMessageWriterServiceTest {

    private ResourceMessageWriterService writerService;
    private MessageService messageService;
    private InOutService inOutService;

    @BeforeEach
    public void setUp() {
        inOutService = Mockito.mock(InOutService.class);
        messageService = Mockito.mock(MessageService.class);
        writerService = new ResourceMessageWriterService(inOutService, messageService);
    }

    @Test
    void writeStringFromSourceOnlyCode() {

        doAnswer(a -> "test").when(messageService).getMessage("test");
        doAnswer(a -> "test").when(inOutService).writeString("test");
        writerService.writeStringFromSource("test");
        verify(inOutService, times(1)).writeString(anyString());
        verify(messageService, times(1)).getMessage(anyString());
    }

    @Test
    void testWriteStringFromSourceCodeWithArgs() {

        doAnswer(a -> "test").when(messageService).getMessage("test", new String[]{"a"});
        doAnswer(a -> "test").when(inOutService).writeString("test");
        writerService.writeStringFromSource("test", "a");
        verify(inOutService, times(1)).writeString(anyString());
        verify(messageService, times(1)).getMessage(anyString(), any());
    }
}