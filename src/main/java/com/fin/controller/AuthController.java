package com.fin.controller;

import com.fin.dto.OtpDto;
import com.fin.dto.ServiceResponse;
import com.fin.dto.UserLoginDto;
import com.fin.model.User;
import com.fin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
public class AuthController {
    private final AuthService authService;

    @Autowired
    AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("signup")
    public ResponseEntity<?> clientSignup(@RequestBody User user){

        ServiceResponse<Boolean> response = authService.registerUser(user);

        if(response.getStatus()) return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("signup/otp")
    public ServiceResponse<?> clientSignupOtp(@RequestBody OtpDto otpDto){

        return new ServiceResponse<>("");
    }

    @PostMapping("login")
    public ServiceResponse<?> clientLogin(@RequestBody UserLoginDto userLoginDto){

        return new ServiceResponse<>("");
    }

    @PostMapping("logout")
    public ServiceResponse<?> clientLogout(){

        return new ServiceResponse<>("");
    }
}
