package hw04.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw04.service.InOutService;
import ru.otus.projs.hw04.service.MessageService;
import ru.otus.projs.hw04.service.ResourceMessageWriterService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ResourceMessageWriterService.class,MessageService.class,InOutService.class})
class ResourceMessageWriterServiceTest {

    @Autowired
    private ResourceMessageWriterService writerService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private InOutService inOutService;


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