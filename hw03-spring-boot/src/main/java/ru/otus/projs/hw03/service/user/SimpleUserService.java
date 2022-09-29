package ru.otus.projs.hw03.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw03.exception.GetUserInfoException;
import ru.otus.projs.hw03.model.User;
import ru.otus.projs.hw03.service.InOutService;
import ru.otus.projs.hw03.service.MessageService;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {

    private final InOutService inOutService;
    private final MessageService messageService;

    @Override
    public User askUserInfo() {
        try {
            inOutService.writeString(messageService.getMessage("text.title.student.enter.name"));
            String firstName = inOutService.readString();
            inOutService.writeString(messageService.getMessage("text.title.student.enter.lastname"));
            String lastName = inOutService.readString();
            return new User(firstName, lastName);
        } catch (Exception e) {
            throw new GetUserInfoException(e);
        }
    }

}
