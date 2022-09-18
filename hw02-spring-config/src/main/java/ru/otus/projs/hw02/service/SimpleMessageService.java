package ru.otus.projs.hw02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@RequiredArgsConstructor
public class SimpleMessageService implements MessageService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, Locale.getDefault());
    }

    @Override
    public String getMessage(String code, Object[] args) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

    @Override
    public String getMessage(String code) throws NoSuchMessageException {
        return messageSource.getMessage(code, new Object[]{}, Locale.getDefault());
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(resolvable, locale);
    }
}
