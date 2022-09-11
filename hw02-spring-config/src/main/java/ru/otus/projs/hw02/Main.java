package ru.otus.projs.hw02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.projs.hw02.service.test_generator.TestGenerator;

@Configuration
@ComponentScan("ru.otus")
public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        TestGenerator testGenerator = ctx.getBean(TestGenerator.class);
        testGenerator.generateTest();

    }

}
