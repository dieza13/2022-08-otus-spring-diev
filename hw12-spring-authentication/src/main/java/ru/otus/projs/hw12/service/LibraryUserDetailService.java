package ru.otus.projs.hw12.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw12.model.User;
import ru.otus.projs.hw12.model.dto.UserInfo;
import ru.otus.projs.hw12.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = null;
            user = userRepository.findByUsername(username);
            user.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
        return UserInfo.toUserInfo(user.get());
    }
}
