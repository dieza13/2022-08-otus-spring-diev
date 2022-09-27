package ru.otus.projs.hw03.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app.params")
@Getter @Setter
public class AppParamsConfig {

    private Locale locale;
    private Integer testPassLimit;

}
