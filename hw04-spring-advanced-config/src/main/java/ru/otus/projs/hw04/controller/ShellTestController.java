package ru.otus.projs.hw04.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.projs.hw04.service.test_executor.TestExecutor;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ShellTestController {

    private final TestExecutor executor;
    private String secretWord;

    @ShellMethod(key = "execute")
    @ShellMethodAvailability(value = "isSecretWordTyped")
    public String executeTest() {
        executor.execute();
        return "Test finished";
    }

    @ShellMethod(key = "type")
    public String typeSecretWord(@ShellOption String secret, @ShellOption String word) {
        this.secretWord = String.format("%s %s",secret, word);
        return "Let's have a test";
    }

    private Availability isSecretWordTyped() {
        return !Optional
                .ofNullable(secretWord)
                .orElse("").equals("secret word") ?
                Availability.unavailable("You should type secret word to have a test") :
                Availability.available();
    }

}
