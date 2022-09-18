package ru.otus.projs.hw02.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.projs.hw02.service.MessageService;
import ru.otus.projs.hw02.service.SimpleMessageService;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        var source = new ResourceBundleMessageSource();
        source.setBasename("messages/message");
        return source;
    }

    @Bean
    public MessageService messageService(MessageSource messageSource) {
        return new SimpleMessageService(messageSource);
    }

}
