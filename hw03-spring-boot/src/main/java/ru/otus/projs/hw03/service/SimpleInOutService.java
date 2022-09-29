package ru.otus.projs.hw03.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw03.exception.InputReadingException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Service
public class SimpleInOutService implements InOutService {

    private final BufferedReader reader;
    private final InputStream in;
    private final PrintStream out;

    public SimpleInOutService(
            @Value("#{ T(java.lang.System).in }") InputStream in,
            @Value("#{ T(java.lang.System).out }") PrintStream out
    ) {
        reader = new BufferedReader(new InputStreamReader(in));
        this.in = in;
        this.out = out;
    }

    @Override
    public String readString() {

        try {
            return reader.readLine();
        } catch (Exception e) {
            throw new InputReadingException(e);
        }

    }

    @Override
    public void writeString(String outString) {
        out.print(outString);
    }


}
