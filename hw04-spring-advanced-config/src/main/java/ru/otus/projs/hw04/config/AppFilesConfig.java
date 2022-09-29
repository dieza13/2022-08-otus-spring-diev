package ru.otus.projs.hw04.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.files")
@Setter
public class AppFilesConfig implements CSVFileNameProvider {

    private String csvQuestionsFileName;

    @Override
    public String getFileName() {
        return csvQuestionsFileName;
    }
}
