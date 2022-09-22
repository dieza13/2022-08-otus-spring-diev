package ru.otus.projs.hw02.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.exception.OnAskQuestionException;
import ru.otus.projs.hw02.model.Question;
import ru.otus.projs.hw02.model.QuestionResult;
import ru.otus.projs.hw02.model.SimpleResult;
import ru.otus.projs.hw02.model.TestResult;
import ru.otus.projs.hw02.model.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SimpleTestingService implements TestingService {

    private final static String ANSWER_OUT_FORMAT = "%d. %s \n";
    private final static String QUESTION_OUT_FORMAT = "%s \n";

    private final InOutService inOutService;
    private final MessageWriterService messageWriterService;
    private final MessageService messageService;

    public SimpleTestingService(
            InOutService inOutService,
            MessageWriterService messageWriterService,
            MessageService messageService
    ) {
        this.inOutService = inOutService;
        this.messageWriterService = messageWriterService;
        this.messageService = messageService;
    }

    @Override
    public TestResult askQuestions(User user, List<Question> questions) {

        messageWriterService.writeStringFromSource("text.title.greeting", user.getFirstName(), user.getLastName());
        List<QuestionResult> results = questions.stream().map(this::askQuestion).collect(Collectors.toList());
        return new TestResult(user, results);

    }

    protected QuestionResult askQuestion(Question question) {
        try {
            inOutService.writeString(String.format(QUESTION_OUT_FORMAT, question.getContent()));
            if (question.getAnswers() != null && question.getAnswers().size() > 1) {
                IntStream
                        .range(0, question.getAnswers().size())
                        .mapToObj(i->
                                String.format(
                                        ANSWER_OUT_FORMAT,
                                        i + 1,
                                        question.getAnswer(i).get().getAnswerContext()
                                )
                        ).forEach(inOutService::writeString);
            }
            String userAnswer = requestAnswer(question);
            return new SimpleResult(question, userAnswer);
        }catch (Exception e) {
            throw new OnAskQuestionException(question, e);
        }
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
