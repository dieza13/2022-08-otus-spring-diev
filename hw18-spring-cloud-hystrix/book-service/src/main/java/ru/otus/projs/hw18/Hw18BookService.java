package ru.otus.projs.hw18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class Hw18BookService {

	public static void main(String[] args)  {
		SpringApplication.run(Hw18BookService.class, args);
	}

}
