package ru.otus.projs.hw03.service;

import ru.otus.projs.hw03.model.Question;
import ru.otus.projs.hw03.model.TestResult;
import ru.otus.projs.hw03.model.User;

import java.util.List;

public interface TestingService {

    TestResult askQuestions(User user, List<Question> questions);

}
