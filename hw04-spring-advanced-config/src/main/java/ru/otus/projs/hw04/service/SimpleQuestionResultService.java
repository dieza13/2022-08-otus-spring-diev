package ru.otus.projs.hw04.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.projs.hw04.config.PassLimitProvider;
import ru.otus.projs.hw04.exception.HandleTestResultException;
import ru.otus.projs.hw04.model.QuestionResult;
import ru.otus.projs.hw04.model.TestResult;

import java.util.List;

@Service
public class SimpleQuestionResultService implements QuestionResultService {

    private final MessageService messageService;
    private final PassLimitProvider passLimitProvider;

    public SimpleQuestionResultService(
            MessageService messageService,
            PassLimitProvider passLimitProvider
    ) {
        this.messageService = messageService;
        this.passLimitProvider = passLimitProvider;
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
            String testPassText = (passLimitProvider.getPassLimit() <= correctAnswersCount) ?
                    messageService.getMessage("text.title.student.test.passed") :
                    messageService.getMessage("text.title.student.test.notPassed");
            if (passLimitProvider.getPassLimit() != null && passLimitProvider.getPassLimit() >= 0)
                stringResult.append(testPassText);

            return stringResult.toString();
        } catch(Exception e) {
            throw new HandleTestResultException(testResults);
        }
    }
}
