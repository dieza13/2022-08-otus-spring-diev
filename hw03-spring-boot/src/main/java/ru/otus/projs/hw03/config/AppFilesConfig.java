package ru.otus.projs.hw03.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.files")
@Getter @Setter
public class AppFilesConfig {

    private String csvQuestions;
}
