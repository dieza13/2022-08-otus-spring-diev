package ru.otus.projs.hw02.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.QuestionResult;
import ru.otus.projs.hw02.model.SimpleResult;
import ru.otus.projs.hw02.model.TestResult;
import ru.otus.projs.hw02.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleTestingService implements TestingService {

    private final static String ANSWER_OUT_FORMAT = "%d. %s \n";
    private final static String QUESTION_OUT_FORMAT = "%s \n";

    private final InOutService inOutService;
    private final MessageService messageService;

    public SimpleTestingService(
            InOutService inOutService,
            MessageService messageService
    ) {
        this.inOutService = inOutService;
        this.messageService = messageService;
    }

    @Override
    public TestResult askQuestions(User user, List<Question> questions) {

        inOutService.writeStringFromSource("text.title.greeting", new String[]{user.getFirstName(), user.getLastName()});
        List<QuestionResult> results = questions.stream().map(this::handleQuestion).collect(Collectors.toList());
        return new TestResult(user, results);

    }

    protected QuestionResult handleQuestion(Question question) {

        inOutService.writeString(String.format(QUESTION_OUT_FORMAT, question.getContent()));
        if (question.getAnswers() != null && question.getAnswers().size() > 1)
            if (question.getAnswers().size() > 1) {
                for (int i = 0; i < question.getAnswers().size(); i++) {
                    if (question.getAnswer(i).isPresent())
                        inOutService.writeString(
                                String.format(
                                        ANSWER_OUT_FORMAT,
                                        i + 1,
                                        question.getAnswer(i).get().getAnswerContext()
                                ));
                }
            }
        String userAnswer = requestAnswer(question);
        return new SimpleResult(question, userAnswer);

    }

    private String requestAnswer(Question question) {
        String answerTitle = (question.getAnswers() != null && question.getAnswers().size() > 1) ?
                messageService.getMessage("text.title.answer.variants") :
                messageService.getMessage("text.title.answer.one");
        inOutService.writeString(answerTitle);
        String typedAnswer = inOutService.readString();
        inOutService.writeString("\n");
        return typedAnswer;
    }

}
