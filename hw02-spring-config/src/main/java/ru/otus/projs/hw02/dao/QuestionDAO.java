package ru.otus.projs.hw02.dao;

import ru.otus.projs.hw02.model.Question;

import java.util.List;

public interface QuestionDAO {

    List<Question> findAll();

}
