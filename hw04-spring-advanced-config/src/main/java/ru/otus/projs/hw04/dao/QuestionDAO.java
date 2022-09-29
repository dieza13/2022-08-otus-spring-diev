package ru.otus.projs.hw04.dao;

import ru.otus.projs.hw04.model.Question;

import java.util.List;

public interface QuestionDAO {

    List<Question> findAll();

}
