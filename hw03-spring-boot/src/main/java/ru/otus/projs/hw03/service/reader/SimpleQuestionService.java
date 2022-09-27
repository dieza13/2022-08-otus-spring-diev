package ru.otus.projs.hw03.service.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw03.dao.QuestionDAO;
import ru.otus.projs.hw03.model.Question;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleQuestionService implements QuestionService {

    private final QuestionDAO questionDAO;

    @Override
    public List<Question> findAll() {
        return questionDAO.findAll();
    }

}
