package ru.otus.projs.hw03.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.projs.hw03.model.User;
import ru.otus.projs.hw03.service.InOutService;
import ru.otus.projs.hw03.service.MessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SimpleUserServiceTest {

    @Autowired
    private SimpleUserService userService;
    @MockBean
    private InOutService inOutService;
    @MockBean
    private MessageService messageService;


    @Test
    void askUserInfo() {
        User user = new User("a","b");
        doNothing().when(inOutService).writeString(any());
        when(inOutService.readString()).thenReturn("a","b");
        User userAnswer = userService.askUserInfo();
        assertEquals(userAnswer.getFirstName(),"a");
        assertEquals(userAnswer.getLastName(),"b");
        verify(inOutService, times(2)).readString();
        verify(inOutService, times(2)).writeString(any());
    }
}