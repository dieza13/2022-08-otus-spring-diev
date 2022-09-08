package ru.otus.projs.hw01.service.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.projs.hw01.model.Question;

import java.util.Arrays;
import java.util.List;

class CSVQuestionReaderTest {

    private CSVQuestionReader questionReader;

    @BeforeEach
    void setUp() {
        questionReader = new CSVQuestionReader("/test_questions.csv");
    }

    @Test
    void getQuestionList_read5questions() {

        List<Question> questions = questionReader.getQuestionList();
        Assertions.assertEquals(questions.size(), 5);
    }

    @Test
    void getQuestionList_variantsInQuestion() {

        List<Question> questions = questionReader.getQuestionList();
        List<Integer> counts = Arrays.asList(2,3,3,3,3);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(questions.get(i).getAnswers().size(),counts.get(i));
        }

    }

    @Test
    void getQuestionList_exactQuestion() {

        List<Question> questions = questionReader.getQuestionList();
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