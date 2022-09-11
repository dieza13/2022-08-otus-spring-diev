package ru.otus.projs.hw02.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class SimpleResult implements TestResult{

    Question question;
    String answer;
    @NonFinal
    Boolean result;

    @Override
    public boolean isSuccess() {

        if (result == null) {
            if (question.getAnswers().size() > 1) {
                try {
                    int answerNum = Integer.valueOf(answer);
                    result = question.getAnswer(answerNum - 1).isCorrect();
                } catch (Exception e) {
                    result = Boolean.FALSE;
                }
            } else {
                result = question.getAnswer(0)
                        .getAnswerContext()
                        .trim()
                        .equalsIgnoreCase(answer.trim());
            }
        }
        return result;

    }

}
