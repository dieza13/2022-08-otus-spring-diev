package ru.otus.projs.hw02.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw02.dao.UserDAO;
import ru.otus.projs.hw02.model.User;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {

    private final UserDAO userDAO;

    @Override
    public User askUserInfo() {
        return userDAO.askUserInfo();
    }
}
