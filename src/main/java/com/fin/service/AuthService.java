package com.fin.service;

import com.fin.dto.ServiceResponse;
import com.fin.model.User;
import com.fin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    AuthService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ServiceResponse<Boolean> registerUser(User user){

        return new ServiceResponse<>("");
    }
}
