package ru.otus.projs.hw02.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Data
public class Question {

    String content;
    List<Answer> answers;

    public Answer getAnswer(int num) {

        return getAnswers().get(num);

    }

}
