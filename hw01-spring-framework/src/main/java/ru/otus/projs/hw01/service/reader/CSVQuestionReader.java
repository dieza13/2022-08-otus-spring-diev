package ru.otus.projs.hw01.service.reader;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.UrlResource;
import org.xml.sax.InputSource;
import ru.otus.projs.hw01.model.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CSVQuestionReader implements QuestionReader {

    String fileName;

    @SneakyThrows
    @Override
    public List<Question> getQuestionList() {

        return readFileToStringArray(fileName)
                .filter(line -> !line.trim().isBlank())
                .map(this::prepareQuestion)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private Stream<String> readFileToStringArray(String fileName) {

        InputStream is = this.getClass().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(is)).lines();

    }

    private Question prepareQuestion(String questionLine) {
        String[] content = questionLine.split(";");
        String cnt = content[0];
        List<String> answers = (content.length > 1) ?
                Arrays.asList(Arrays.copyOfRange(content, 1, content.length)) : new ArrayList<>();
        return new Question(cnt, answers);
    }
}
