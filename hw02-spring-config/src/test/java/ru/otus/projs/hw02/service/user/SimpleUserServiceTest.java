package ru.otus.projs.hw02.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw02.dao.UserDAO;
import ru.otus.projs.hw02.model.User;
import ru.otus.projs.hw02.service.SimpleMessageService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimpleUserServiceTest {

    private SimpleUserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = Mockito.mock(UserDAO.class);
        userService = new SimpleUserService(userDAO);
    }

    @Test
    void askUserInfo() {
        User user = new User("a","b");
        when(userDAO.askUserInfo()).thenReturn(user);
        User userAnswer = userService.askUserInfo();
        assertEquals(user,userAnswer);
        verify(userDAO, times(1)).askUserInfo();
    }
}