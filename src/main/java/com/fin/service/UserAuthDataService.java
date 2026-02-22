package com.fin.service;

import com.fin.dto.UserLoginDto;
import com.fin.repository.UserAuthDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthDataService {

    private final UserAuthDataRepository userAuthDataRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthDataService(UserAuthDataRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userAuthDataRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public UserLoginDto saveUser(UserLoginDto userAuthData) {
//        userAuthData.setUserPassword(passwordEncoder.encode(userAuthData.getUserPassword()));
//        return userAuthDataRepository.save(userAuthData);
//    }
}

/*  User Registration → Saves new users to the database after encrypting passwords.*/