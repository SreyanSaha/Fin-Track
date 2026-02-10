package com.fin.controller;

import com.fin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
public class AuthController {
    private final AuthService authService;

    @Autowired
    AuthController(AuthService authService){
        this.authService=authService;
    }
}
