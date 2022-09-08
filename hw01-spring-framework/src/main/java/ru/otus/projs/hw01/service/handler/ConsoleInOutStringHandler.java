package ru.otus.projs.hw01.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConsoleInOutStringHandler implements InOutStringHandler {

    static String ERR_CONSOLE_READING = "Console reading error: ";
    BufferedReader reader;
    InputStream in;
    PrintStream out;

    public ConsoleInOutStringHandler(InputStream in, PrintStream out) {
        reader = new BufferedReader(new InputStreamReader(in));
        this.in = in;
        this.out = out;
    }

    @Override
    public String readString() {

        try{
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(ERR_CONSOLE_READING, e);
        }

    }

    @Override
    public void writeString(String outString) {
        out.print(outString);
    }

}
