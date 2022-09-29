package ru.otus.projs.hw03.dao;

import ru.otus.projs.hw03.model.Question;

import java.util.List;

public interface QuestionDAO {

    List<Question> findAll();

}
