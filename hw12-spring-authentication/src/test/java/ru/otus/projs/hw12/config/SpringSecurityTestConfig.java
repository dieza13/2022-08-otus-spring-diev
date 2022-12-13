package ru.otus.projs.hw12.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.otus.projs.hw12.model.User;
import ru.otus.projs.hw12.model.dto.UserInfo;

import java.util.Arrays;

//@TestConfiguration
public class SpringSecurityTestConfig {

//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService() {
//        UserInfo basicUser = new UserInfo("admin", "admin");
//        UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_USER")
//        ));
//
//        return new InMemoryUserDetailsManager(Arrays.asList(
//                basicActiveUser
//        ));
//    }
}
