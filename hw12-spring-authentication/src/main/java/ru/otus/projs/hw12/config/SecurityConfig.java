package ru.otus.projs.hw12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.projs.hw12.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.ALWAYS )
                .and()
                .authorizeRequests()
                .antMatchers("/api/login","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe().key("super-secret").tokenValiditySeconds(60 * 30)
                .and()
                .anonymous().principal("secret_user")
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,UserDetailService userDetailService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
