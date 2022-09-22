package ru.otus.projs.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimpleResultPrinterTest {

    private SimpleInOutService inOutService;
    private SimpleResultPrinter simpleResultPrinter;

    @BeforeEach
    void setUp() {
        inOutService = Mockito.mock(SimpleInOutService.class);
        simpleResultPrinter = new SimpleResultPrinter(inOutService);
    }

    @Test
    void print() {

        doNothing().when(inOutService).writeString(any());
        simpleResultPrinter.print("test");
        verify(inOutService, times(1)).writeString(any());

    }
}