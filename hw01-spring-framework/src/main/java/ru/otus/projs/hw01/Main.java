package ru.otus.projs.hw01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.projs.hw01.service.test_generator.TestGenerator;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestGenerator testGenerator = ctx.getBean(TestGenerator.class);

        testGenerator.generateTest();

    }

}
