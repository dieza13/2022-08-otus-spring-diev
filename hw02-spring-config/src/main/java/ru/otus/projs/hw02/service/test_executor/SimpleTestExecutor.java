package ru.otus.projs.hw02.service.test_executor;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.TestResult;
import ru.otus.projs.hw02.model.User;
import ru.otus.projs.hw02.service.*;
import ru.otus.projs.hw02.service.reader.QuestionService;
import ru.otus.projs.hw02.service.user.UserService;

import java.util.List;

@Service
public class SimpleTestExecutor implements TestExecutor {

    private final QuestionResultService questionResultService;
    private final QuestionService questionService;
    private final UserService userService;
    private final TestingService testingService;
    private final ResultPrinter resultPrinterService;
    private final MessageWriterService writerService;

    public SimpleTestExecutor(
            QuestionService questionService,
            UserService userService,
            TestingService testingService,
            QuestionResultService questionResultService,
            ResultPrinter resultPrinter,
            MessageWriterService writerService
    ) {
        this.questionResultService = questionResultService;
        this.testingService = testingService;
        this.questionService = questionService;
        this.userService = userService;
        this.resultPrinterService = resultPrinter;
        this.writerService = writerService;
    }

    public void execute() {

        try {
            List<Question> questions = questionService.findAll();
            User user = userService.askUserInfo();
            TestResult testResult = testingService.askQuestions(user, questions);
            String result = questionResultService.handleResult(testResult);
            resultPrinterService.print(result);
        } catch (Exception e) {
            writerService.writeStringFromSource("err.main.handle", e.getMessage());
        }
    }

}
