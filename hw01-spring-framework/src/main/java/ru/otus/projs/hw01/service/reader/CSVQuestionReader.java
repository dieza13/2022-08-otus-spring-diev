package ru.otus.projs.hw01.service.reader;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.model.Answer;
import ru.otus.projs.hw01.model.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CSVQuestionReader implements QuestionReader {

    String fileName;

    static String ERR_READ_RESOURCE = "Ошибка чтения ресурса %s: ";

    @Override
    public List<Question> getQuestionList() {

        return readFileToStringArray(fileName)
                .stream()
                .filter(line -> !line.trim().isBlank())
                .map(this::prepareQuestion)
                .collect(Collectors.toList());

    }

    private List<String> readFileToStringArray(String fileName) {
        try (InputStream is = this.getClass().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            new RuntimeException(String.format(ERR_READ_RESOURCE, fileName),  e);
        }
        return null;
    }

    private Question prepareQuestion(String questionLine) {
        String[] content = questionLine.split(";");
        String cnt = content[0];
        List<Answer> answers = (content.length > 1)
                ?
                Arrays.asList(Arrays.copyOfRange(content, 1, content.length))
                        .stream()
                        .map(Answer::new)
                        .collect(Collectors.toList())
                :
                new ArrayList<>();
        return new Question(cnt, answers);
    }

}
