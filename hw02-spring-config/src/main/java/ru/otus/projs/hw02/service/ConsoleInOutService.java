package ru.otus.projs.hw02.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.exception.ETestException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Service
public class ConsoleInOutService implements InOutService {

    private final MessageService messageService;
    private final BufferedReader reader;
    private final InputStream in;
    private final PrintStream out;

    public ConsoleInOutService(
            MessageService messageService,
            @Value("#{ T(java.lang.System).in }")InputStream in,
            @Value("#{ T(java.lang.System).out }")PrintStream out
    ) {
        this.messageService = messageService;
        reader = new BufferedReader(new InputStreamReader(in));
        this.in = in;
        this.out = out;
    }

    @Override
    public String readString() {

        try{
            return reader.readLine();
        } catch (Exception e) {
            throw new ETestException(messageService.getMessage("err.console.reading"), e);
        }

    }

    @Override
    public void writeString(String outString) {
        out.print(outString);
    }

    @Override
    public void writeStringFromSource(String code) {
        out.print(messageService.getMessage(code));
    }

    @Override
    public void writeStringFromSource(String code, String[] args) {
        out.print(messageService.getMessage(code, args));
    }



}
