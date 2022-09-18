package ru.otus.projs.hw02.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.projs.hw02.model.User;
import ru.otus.projs.hw02.service.InOutService;
import ru.otus.projs.hw02.service.MessageService;

@Component
@RequiredArgsConstructor
public class SimpleUserDAO implements UserDAO {

    private final InOutService inOutService;
    private final MessageService messageService;

    @Override
    public User askUserInfo() {
        inOutService.writeString(messageService.getMessage("text.title.student.enter.name"));
        String firstName = inOutService.readString();
        inOutService.writeString(messageService.getMessage("text.title.student.enter.lastname"));
        String lastName = inOutService.readString();
        return new User(firstName, lastName);
    }

}
