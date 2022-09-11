package ru.otus.projs.hw02.service.reader;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.model.Answer;
import ru.otus.projs.hw02.model.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CSVQuestionReader implements QuestionReader {

    String fileName;
    String errNoAnswersSet;
    String errReadResource;


    public CSVQuestionReader(
            @Value("${file.csv.test}")String fileName,
            @Value("${err.resource.reading}")String errReadResource,
            @Value("${err.resource.noanswer}")String errNoAnswersSet
    ) {

        this.fileName = fileName;
        this.errReadResource = errReadResource;
        this.errNoAnswersSet = errNoAnswersSet;

    }

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
            throw new RuntimeException(String.format(errReadResource, fileName),  e);
        }

    }

    private Question prepareQuestion(String questionLine) {

        String[] content = questionLine.split(";");
        if (content.length < 2) {
            throw new RuntimeException(errNoAnswersSet);
        }
        String cnt = content[0];
        List<Answer> answers = (content.length > 1)
                ?
                Arrays.stream(Arrays.copyOfRange(content, 1, content.length))
                        .map(answ -> {
                            return (answ.trim().startsWith("+++")) ?
                                    new Answer(answ.trim().replace("+++", ""), true)
                                    :
                                    new Answer(answ, false);
                        })
                        .collect(Collectors.toList())
                :
                new ArrayList<>();
        return new Question(cnt, answers);

    }

}
