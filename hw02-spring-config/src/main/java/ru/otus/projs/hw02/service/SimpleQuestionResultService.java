package ru.otus.projs.hw02.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.projs.hw02.exception.HandleTestResultException;
import ru.otus.projs.hw02.model.QuestionResult;
import ru.otus.projs.hw02.model.TestResult;

import java.util.List;

@Service
public class SimpleQuestionResultService implements QuestionResultService {

    private final MessageService messageService;
    private final Integer passLimit;

    public SimpleQuestionResultService(
            MessageService messageService,
            @Value("${param.test.pass.limit}")Integer passLimit
    ) {
        this.messageService = messageService;
        this.passLimit = passLimit;
    }

    @Override
    public String handleResult(TestResult testResults) {

        try {
            StringBuilder stringResult = new StringBuilder(messageService.getMessage("text.title.student.test.result"));
            List<QuestionResult> questionResults = testResults.getQuestionResults();
            if (CollectionUtils.isEmpty(questionResults)) {
                return messageService.getMessage("text.title.student.test.noResults");
            }

            int questionCount = questionResults.size();
            long correctAnswersCount = questionResults
                    .stream()
                    .filter(QuestionResult::isSuccess)
                    .count();

            stringResult.append(messageService.getMessage("text.title.student.test.result.output", (int)questionCount, (int)correctAnswersCount));
            String testPassText = (passLimit <= correctAnswersCount) ?
                    messageService.getMessage("text.title.student.test.passed") :
                    messageService.getMessage("text.title.student.test.notPassed");
            if (passLimit != null && passLimit >= 0)
                stringResult.append(testPassText);

            return stringResult.toString();
        } catch(Exception e) {
            throw new HandleTestResultException(testResults);
        }
    }
}
