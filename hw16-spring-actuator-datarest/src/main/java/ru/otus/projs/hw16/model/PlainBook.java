package ru.otus.projs.hw16.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "plain",
        types = { Book.class })
public interface PlainBook {
    String getName();

    @Value("#{target.getAuthor().getName()} #{target.getAuthor().getLastName()}")
    String getAuthor();

    @Value("#{target.getGenre().getName()}")
    String getGenre();

}
