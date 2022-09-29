package ru.otus.projs.hw04.model;

import java.util.List;

public class MultiAnswerQuestion extends Question {

    private final Answer correctAnswer;

    public MultiAnswerQuestion(String content, List<Answer> answers) {
        super(content, answers);
        correctAnswer = answers.stream().filter(Answer::isCorrect).findFirst().get();
    }

    @Override
    public Answer getCorrectAnswer() {
        return correctAnswer;
    }


    @Override
    public boolean isCorrectAnswer(String answer) {

        boolean result = false;
        try {
            result = getAnswer(Integer.valueOf(answer.trim()) - 1).get().isCorrect();
        } catch (Exception e) {
        }
        return result;
    }

}
