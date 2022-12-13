package ru.otus.projs.hw12.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserView {
    private final String username;
    private final String password;

    public static UserView toUserView(UserInfo user) {
        return new UserView(user.getUsername(), user.getPassword());
    }
}
