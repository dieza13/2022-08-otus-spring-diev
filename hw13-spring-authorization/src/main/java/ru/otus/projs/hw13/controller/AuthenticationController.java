package ru.otus.projs.hw13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.projs.hw13.model.dto.UserInfo;
import ru.otus.projs.hw13.model.dto.UserView;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;


    @PostMapping(path = {"/api/login","/login"})
    public ResponseEntity<UserView> loginRedirect(@RequestBody UserInfo user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        auth = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok().body(UserView.toUserView(user));
    }



}
