package ru.otus.projs.hw01.service.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.model.Question;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TitleOutputPreHandler implements QuestionsPreHandler{

    InOutStringHandler inOutStringHandler;
    String title;
    static String TITLE_FORMAT = "%s \n";

    @Override
    public List<Question> preHandleQuestions(List<Question> questions) {

        inOutStringHandler.writeString(String.format(TITLE_FORMAT,title));
        return questions;

    }

}
