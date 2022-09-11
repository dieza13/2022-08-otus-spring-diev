package ru.otus.projs.hw02.service.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.model.Question;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentGreetingPreHandler implements QuestionsPreHandler{

    InOutStringHandler inOutStringHandler;
    String title;
    static String TITLE_FORMAT = "%s \n";
    static String CALL_STUDENT_NAME_FORMAT = "Enter your name, please! \n";

    public StudentGreetingPreHandler(
            InOutStringHandler inOutStringHandler,
            @Value("${text.title}") String title
    ) {

        this.inOutStringHandler = inOutStringHandler;
        this.title = title;

    }

    @Override
    public List<Question> preHandleQuestions(List<Question> questions) {

        inOutStringHandler.writeString(String.format(TITLE_FORMAT,title));
        inOutStringHandler.writeString(CALL_STUDENT_NAME_FORMAT);
        String studentName = inOutStringHandler.readString();

        return questions;

    }

}
