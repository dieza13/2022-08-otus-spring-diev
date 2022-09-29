package ru.otus.projs.hw04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.projs.hw04.config.AppFilesConfig;
import ru.otus.projs.hw04.config.AppParamsConfig;

@SpringBootApplication
@EnableConfigurationProperties({AppParamsConfig.class, AppFilesConfig.class})
public class Hw04SpringBootAdvancedConfig {

	public static void main(String[] args) {
		SpringApplication.run(Hw04SpringBootAdvancedConfig.class, args);
	}

}
