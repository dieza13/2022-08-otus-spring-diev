package ru.otus.projs.hw03.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.projs.hw03.config.AppParamsConfig;
import ru.otus.projs.hw03.exception.HandleTestResultException;
import ru.otus.projs.hw03.model.QuestionResult;
import ru.otus.projs.hw03.model.TestResult;

import java.util.List;

@Service
public class SimpleQuestionResultService implements QuestionResultService {

    private final MessageService messageService;
    private final AppParamsConfig paramConfig;

    public SimpleQuestionResultService(
            MessageService messageService,
            AppParamsConfig paramConfig
    ) {
        this.messageService = messageService;
        this.paramConfig = paramConfig;
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
            String testPassText = (paramConfig.getTestPassLimit() <= correctAnswersCount) ?
                    messageService.getMessage("text.title.student.test.passed") :
                    messageService.getMessage("text.title.student.test.notPassed");
            if (paramConfig.getTestPassLimit() != null && paramConfig.getTestPassLimit() >= 0)
                stringResult.append(testPassText);

            return stringResult.toString();
        } catch(Exception e) {
            throw new HandleTestResultException(testResults);
        }
    }
}
