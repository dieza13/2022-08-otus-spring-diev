package ru.otus.projs.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.projs.hw15.integration.config.ServicesConfig;


@SpringBootApplication
@EnableConfigurationProperties({ServicesConfig.class})
public class Hw15SpringIntegrationApplication {


	public static void main(String[] args)  {
		SpringApplication.run(Hw15SpringIntegrationApplication.class, args);

	}



}
