package ru.otus.projs.hw01.service.test_generator;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.projs.hw01.model.Question;
import ru.otus.projs.hw01.model.QuestionResult;
import ru.otus.projs.hw01.service.reader.QuestionReader;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public abstract class TestGenerator {

    QuestionReader questionReader;

    protected List<Question> preGenerate(List<Question> questions) {
        return questions;
    }
    protected abstract void postGenerate(List<QuestionResult> results);
    protected abstract List<QuestionResult> generate(List<Question> questions);

    public void generateTest() {

        List<Question> questions = questionReader.getQuestionList();
        List<Question> modifiedQuestions = preGenerate(questions);
        List<QuestionResult> results = generate(modifiedQuestions);
        postGenerate(results);

    }

}
