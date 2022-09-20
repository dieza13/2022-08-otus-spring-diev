package ru.otus.projs.hw02.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.projs.hw02.exception.QuestionNoAnswerException;
import ru.otus.projs.hw02.exception.QuestionsResourceReadingException;
import ru.otus.projs.hw02.factory.QuestionFactory;
import ru.otus.projs.hw02.model.Answer;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.service.MessageService;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class CSVQuestionDAO implements QuestionDAO {


    private final String fileName;
    private final QuestionFactory questionFactory;
    private final CSVParser parser;

    public CSVQuestionDAO(
            @Value("${file.csv.questions}") String fileName,
            QuestionFactory questionFactory,
            MessageService messageService
    ) {
        this.fileName = fileName;
        this.questionFactory = questionFactory;
        parser = new CSVParserBuilder().withSeparator(';').build();
    }

    @Override
    public List<Question> findAll() {
        return readFileToStringArray(fileName)
                .stream()
                .map(this::prepareQuestion)
                .collect(Collectors.toList());
    }


    private List<String[]> readFileToStringArray(String fileName) {

        try (
                CSVReader reader = new CSVReaderBuilder(
                        new InputStreamReader(this.getClass().getResourceAsStream(fileName)))
                        .withCSVParser(parser)
                        .build()
        ) {
            return reader.readAll();
        } catch (Exception e) {
            throw new QuestionsResourceReadingException(fileName, e);
        }

    }

    private Question prepareQuestion(String[] questionContent) {

        if (questionContent.length < 2) {
            throw new QuestionNoAnswerException(Optional.ofNullable(questionContent).orElse(new String[]{"NO ANSWER CONTENT"})[0]);
        }
        String content = questionContent[0];
        List<Answer> answers = (questionContent.length > 1)
                ?
                Arrays.stream(Arrays.copyOfRange(questionContent, 1, questionContent.length))
                        .map(answ -> {
                            return (answ.trim().startsWith("+++")) ?
                                    new Answer(answ.trim().replace("+++", ""), true)
                                    :
                                    new Answer(answ, false);
                        })
                        .collect(Collectors.toList())
                :
                new ArrayList<>();

        return questionFactory.createQuestion(content, answers);

    }


}

