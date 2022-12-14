package ru.otus.projs.hw04.service;

import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;


public interface MessageService {

    String getMessage(String code, @Nullable String defaultMessage, @Nullable Object ... args);

    String getMessage(String code, @Nullable Object ... args) throws NoSuchMessageException;

    String getMessage(String code) throws NoSuchMessageException;

}
