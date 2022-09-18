package ru.otus.projs.hw02.service;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

public interface MessageService extends MessageSource {

    String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage);

    String getMessage(String code, @Nullable Object[] args) throws NoSuchMessageException;

    String getMessage(String code) throws NoSuchMessageException;

}
