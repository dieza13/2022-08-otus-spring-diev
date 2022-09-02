package ru.otus.projs.hw01.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Data
public class Question {

    String content;
    List<? extends Object> answers;

    public String toString() {
        return content.concat("\n")
                .concat(answers
                        .stream()
                        .map(line -> String.format("- %s", line))
                        .collect(Collectors.joining("\n")))
                .concat("\n");
    }

}
