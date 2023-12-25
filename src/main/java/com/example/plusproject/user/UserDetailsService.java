package com.example.plusproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserRepository userRepository;
    public UserDetails getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new UsernameNotFoundException("Not Found" + username));
        return new UserDetailsImpl(user);
    }
}
