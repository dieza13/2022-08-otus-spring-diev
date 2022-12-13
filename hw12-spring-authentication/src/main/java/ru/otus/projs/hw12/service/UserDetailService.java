package ru.otus.projs.hw12.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.projs.hw12.model.User;
import ru.otus.projs.hw12.model.dto.UserInfo;
import ru.otus.projs.hw12.repository.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Map<String, User> users = new ConcurrentHashMap<>();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = null;
        if (!users.containsKey(username)) {
            user = userRepository.findByUsername(username);
            user.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
            users.put(username, user.get());
        }
        return UserInfo.toUserInfo(users.get(username));
    }
}
