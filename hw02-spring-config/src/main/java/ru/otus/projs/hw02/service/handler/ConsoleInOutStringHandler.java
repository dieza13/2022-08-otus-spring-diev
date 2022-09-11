package ru.otus.projs.hw02.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConsoleInOutStringHandler implements InOutStringHandler {

    String errConsoleReading;
    BufferedReader reader;
    InputStream in;
    PrintStream out;

    public ConsoleInOutStringHandler(
            @Value("${err.console.reading}") String errConsoleReading,
            @Value("#{ T(java.lang.System).in }")InputStream in,
            @Value("#{ T(java.lang.System).out }")PrintStream out
    ) {
        this.errConsoleReading = errConsoleReading;
        reader = new BufferedReader(new InputStreamReader(in));
        this.in = in;
        this.out = out;
    }

    @Override
    public String readString() {

        try{
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(errConsoleReading, e);
        }

    }

    @Override
    public void writeString(String outString) {
        out.print(outString);
    }

}
