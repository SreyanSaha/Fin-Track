package com.fin.service;

import com.fin.model.User;
import com.fin.repository.UserAuthDataRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthDataRepository userAuthDataRepository;

    public CustomUserDetailsService(UserAuthDataRepository userRepository) {
        this.userAuthDataRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userAuthDataRepository.findByUserName(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getUserPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}


/*
 * Fetches user details from UserRepository.

 */