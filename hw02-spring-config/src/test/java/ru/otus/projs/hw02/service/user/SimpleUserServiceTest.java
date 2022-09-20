package ru.otus.projs.hw02.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.projs.hw02.model.User;
import ru.otus.projs.hw02.service.InOutService;
import ru.otus.projs.hw02.service.MessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {

    private SimpleUserService userService;
    private InOutService inOutService;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        inOutService = Mockito.mock(InOutService.class);
        messageService = Mockito.mock(MessageService.class);
        userService = new SimpleUserService(inOutService,messageService);
    }

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