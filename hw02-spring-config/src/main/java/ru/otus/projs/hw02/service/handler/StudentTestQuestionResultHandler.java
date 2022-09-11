package ru.otus.projs.hw02.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.projs.hw02.model.TestResult;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentTestQuestionResultHandler implements QuestionResultHandler{

    InOutStringHandler inOutStringHandler;
    String footer;
    static String STUDENT_RESULT_TEXT_OUTPUT_FORMAT = "Student test result: \n";
    static String STUDENT_RESULT_OUTPUT_FORMAT = "Question count: %d; Correct answer count: %d\n";
    static String STUDENT_TEST_PASSED = "Test passed\n";
    static String STUDENT_TEST_NOT_PASSED = "Test not passed\n";

    Integer passLimit;

    public StudentTestQuestionResultHandler(
            InOutStringHandler inOutStringHandler,
            @Value("${text.footer}")String footer,
            @Value("${param.test.pass.limit}")Integer passLimit
    ) {
        this.inOutStringHandler = inOutStringHandler;
        this.footer = footer;
        this.passLimit = passLimit;
    }

    @Override
    public void handleResult(List<TestResult> questionResults) {

        if(CollectionUtils.isEmpty(questionResults)) {
            return;
        }

        inOutStringHandler.writeString(STUDENT_RESULT_TEXT_OUTPUT_FORMAT);
        int questionCount = questionResults.size();
        long correctAnswersCount = questionResults
                .stream()
                .filter(TestResult::isSuccess)
                .count();

        inOutStringHandler.writeString(String.format(STUDENT_RESULT_OUTPUT_FORMAT, questionCount, correctAnswersCount));
        String testPassText = (passLimit <= correctAnswersCount) ? STUDENT_TEST_PASSED :  STUDENT_TEST_NOT_PASSED;
        if (passLimit != null && passLimit >= 0)
            inOutStringHandler.writeString(testPassText);
        inOutStringHandler.writeString(footer);

    }
}
