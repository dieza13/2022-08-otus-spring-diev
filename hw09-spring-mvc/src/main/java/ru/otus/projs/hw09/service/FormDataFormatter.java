package ru.otus.projs.hw09.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw09.model.Author;

@Service("formatter")
public class FormDataFormatter implements DataFormatter {

    private static final String SIMPLE_AUTHOR_SHOW_FORMAT = "%s. %s";

    @Override
    public String authorFormat(Author author) {
        return String.format(SIMPLE_AUTHOR_SHOW_FORMAT, author.getName().charAt(0), author.getLastName());
    }
}
