package ru.otus.projs.hw03.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import ru.otus.projs.hw03.config.CSVFileNameProvider;
import ru.otus.projs.hw03.exception.QuestionNoAnswerException;
import ru.otus.projs.hw03.exception.QuestionsResourceReadingException;
import ru.otus.projs.hw03.factory.QuestionFactory;
import ru.otus.projs.hw03.model.Answer;
import ru.otus.projs.hw03.model.Question;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class CSVQuestionDAO implements QuestionDAO {


    private final QuestionFactory questionFactory;
    private final CSVParser parser;
    private final CSVFileNameProvider CSVFileNameProvider;

    public CSVQuestionDAO(
            CSVFileNameProvider CSVFileNameProvider,
            QuestionFactory questionFactory
    ) {
        this.questionFactory = questionFactory;
        parser = new CSVParserBuilder().withSeparator(';').build();
        this.CSVFileNameProvider = CSVFileNameProvider;
    }

    @Override
    public List<Question> findAll() {
        return readFileToStringArray(CSVFileNameProvider.getFileName())
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

