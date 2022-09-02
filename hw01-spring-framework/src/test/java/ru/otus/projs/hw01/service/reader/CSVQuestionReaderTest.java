package ru.otus.projs.hw01.service.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import ru.otus.projs.hw01.model.Question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CSVQuestionReaderTest {

    CSVQuestionReader questionReader;
    List<Question> questions;

    @BeforeEach
    void setUp() {
        questionReader = new CSVQuestionReader("/test_questions.csv");
        questions = questionReader.getQuestionList();
    }

    @Test
    void getQuestionList_read5questions() {
        Assertions.assertEquals(questions.size(), 5);
    }

    @Test
    void getQuestionList_variantsInQuestion() {

        List<Integer> counts = Arrays.asList(2,3,3,3,3);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(questions.get(i).getAnswers().size(),counts.get(i));
        }

    }

    @Test
    void getQuestionList_exactQuestion() {

        List<String> questionContent = Arrays.asList("To be, or not to be.."
                ,"What did Charlie answer to Turkish?"
                ,"Who wants to live forever?"
                ,"The first rule of fight club"
                ,"Who is the best joker?");
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(questions.get(i).getContent(),questionContent.get(i));
        }

    }


}