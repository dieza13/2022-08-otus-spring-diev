package ru.otus.projs.hw04.service;

import ru.otus.projs.hw04.model.Question;
import ru.otus.projs.hw04.model.TestResult;
import ru.otus.projs.hw04.model.User;

import java.util.List;

public interface TestingService {

    TestResult askQuestions(User user, List<Question> questions);

}
