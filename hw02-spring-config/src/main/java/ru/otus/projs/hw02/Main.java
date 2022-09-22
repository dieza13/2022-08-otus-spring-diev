package ru.otus.projs.hw02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.projs.hw02.service.test_executor.TestExecutor;

@Configuration
@ComponentScan("ru.otus.projs.hw02")
public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        TestExecutor testExecutor = ctx.getBean(TestExecutor.class);
        testExecutor.execute();

    }

}
