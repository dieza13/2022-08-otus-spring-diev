package ru.otus.projs.hw01.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConsoleInOutStringHandler implements InOutStringHandler {

    static String ERR_CONSOLE_READING = "Ошибка чтения данных из консоли: ";

    @Override
    public String readString() {

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(ERR_CONSOLE_READING, e);
        }

    }

    @Override
    public void writeString(String outString) {
        System.out.print(outString);
    }

}
