package hw04.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw04.service.MessageService;
import ru.otus.projs.hw04.service.SimpleInOutService;
import ru.otus.projs.hw04.service.SimpleMessageService;

import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

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
        inOutService = new SimpleInOutService(inputStream, printStream);
    }

    @Test
    void writeString() {

        doAnswer(a -> "test").when(printStream).print("test");

        inOutService.writeString("test");

        verify(printStream, times(1)).print(anyString());
    }
}