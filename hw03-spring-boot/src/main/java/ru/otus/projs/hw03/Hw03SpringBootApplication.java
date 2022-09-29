package ru.otus.projs.hw03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.projs.hw03.config.AppFilesConfig;
import ru.otus.projs.hw03.config.AppParamsConfig;
import ru.otus.projs.hw03.service.test_executor.TestExecutor;

@SpringBootApplication
@EnableConfigurationProperties({AppParamsConfig.class, AppFilesConfig.class})
public class Hw03SpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Hw03SpringBootApplication.class, args);
		TestExecutor executor = ctx.getBean(TestExecutor.class);
		executor.execute();
	}

}
