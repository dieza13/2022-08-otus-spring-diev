package ru.otus.projs.hw01.service.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.model.TestResult;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OutputQuestionResultHandler implements QuestionResultHandler{

    InOutStringHandler inOutStringHandler;
    String footer;

    @Override
    public void handleResult(List<TestResult> questionResults) {

        inOutStringHandler.writeString(footer);

    }
}
