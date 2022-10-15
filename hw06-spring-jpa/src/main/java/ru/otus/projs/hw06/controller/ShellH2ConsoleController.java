package ru.otus.projs.hw06.controller;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellH2ConsoleController {

    @ShellMethod(key = "h2-console")
    public String startH2Console() throws Exception {
        Console.main();
        return "console started";
    }

}
