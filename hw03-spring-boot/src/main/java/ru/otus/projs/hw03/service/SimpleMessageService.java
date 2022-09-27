package ru.otus.projs.hw03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw03.config.AppParamsConfig;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SimpleMessageService implements MessageService {

    private final MessageSource messageSource;
    private final AppParamsConfig paramsConfig;

    @Override
    public String getMessage(String code, String defaultMessage, Object ... args) {
        return messageSource.getMessage(code, args, defaultMessage, Locale.getDefault());
    }

    @Override
    public String getMessage(String code, Object ... args) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, paramsConfig.getLocale());
    }

    @Override
    public String getMessage(String code) throws NoSuchMessageException {
        return messageSource.getMessage(code, new Object[]{}, paramsConfig.getLocale());
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
