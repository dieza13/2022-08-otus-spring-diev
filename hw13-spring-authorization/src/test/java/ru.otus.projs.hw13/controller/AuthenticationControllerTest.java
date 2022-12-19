package ru.otus.projs.hw13.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.projs.hw13.config.SecurityConfig;
import ru.otus.projs.hw13.model.dto.UserInfo;
import ru.otus.projs.hw13.model.dto.UserView;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер по аутентификации")
@WebMvcTest(value = AuthenticationController.class)
@Import(SecurityConfig.class)
public class AuthenticationControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void loginRedirect() throws Exception {

        when(authenticationManager.authenticate(any())).thenReturn(new TestAuthentication() {
        });

        String json = "{ \"username\" : \"fedor\", \"password\" : \"1\" }";
        UserInfo user = mapper.readValue(json, UserInfo.class);

        UserView resUser = UserView.toUserView(user);
        mvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(resUser)));

    }

    @WithAnonymousUser
    @Test
    void loginRedirect_withUser() throws Exception {

        when(authenticationManager.authenticate(any())).thenReturn(new TestAuthentication() {
        });

        String json = "{ \"username\" : \"fedor\", \"password\" : \"1\" }";
        UserInfo user = mapper.readValue(json, UserInfo.class);

        UserView resUser = UserView.toUserView(user);
        mvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(resUser)));

    }

    public static class TestAuthentication implements Authentication {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return "anonymous";
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return "admin";
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "admin";
        }
    }
}