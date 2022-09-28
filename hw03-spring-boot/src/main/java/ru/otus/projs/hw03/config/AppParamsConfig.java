package ru.otus.projs.hw03.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app.params")
@Setter
public class AppParamsConfig implements LocaleProvider, PassLimitProvider {

    private Locale locale;
    private Integer testPassLimit;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Integer getPassLimit() {
        return testPassLimit;
    }
}
