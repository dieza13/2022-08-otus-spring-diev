package ru.otus.projs.hw02.service.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.projs.hw02.model.Question;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CSVQuestionReader.class})
@TestPropertySource("classpath:test-application.properties")
class CSVQuestionReaderTest {

    @Autowired
    private QuestionReader questionReader;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getQuestionList_read5questions() {

        List<Question> questions = questionReader.getQuestionList();
        Assertions.assertEquals(questions.size(), 5);
    }

    @Test
    void getQuestionList_variantsInQuestion() {

        List<Question> questions = questionReader.getQuestionList();
        List<Integer> counts = Arrays.asList(2,3,1,3,3);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(questions.get(i).getAnswers().size(),counts.get(i));
        }

    }

    @Test
    void getQuestionList_exactQuestion() {

        List<Question> questions = questionReader.getQuestionList();
        List<String> questionContent = Arrays.asList("To be, or not to be.."
                ,"What did Charlie answer to Turkish?"
                ,"Model of terminator in Terminator 1"
                ,"The first rule of fight club"
                ,"Who is the best joker?");
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(questions.get(i).getContent(),questionContent.get(i));
        }

    }

    @Test
    void getQuestionList_badFileErr() {
        QuestionReader reader = new CSVQuestionReader("badFile","someErr","noAnswer");
        Exception e = Assertions.assertThrows(RuntimeException.class, reader::getQuestionList);
        Assertions.assertEquals(e.getMessage(), "someErr");
    }

    @Test
    void getQuestionList_noAnswerSetErr() {
        QuestionReader reader = new CSVQuestionReader("/test_questions_bad.csv","someErr","noAnswer");
        Exception e = Assertions.assertThrows(RuntimeException.class, reader::getQuestionList);
        Assertions.assertEquals(e.getMessage(), "noAnswer");
    }


}