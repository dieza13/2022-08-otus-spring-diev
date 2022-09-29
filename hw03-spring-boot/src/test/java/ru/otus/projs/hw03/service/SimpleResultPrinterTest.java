package ru.otus.projs.hw03.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {SimpleResultPrinter.class, SimpleInOutService.class})
class SimpleResultPrinterTest {

    @MockBean
    private SimpleInOutService inOutService;
    @Autowired
    private SimpleResultPrinter simpleResultPrinter;

    @Test
    void print() {

        doNothing().when(inOutService).writeString(any());
        simpleResultPrinter.print("test");
        verify(inOutService, times(1)).writeString(any());

    }
}