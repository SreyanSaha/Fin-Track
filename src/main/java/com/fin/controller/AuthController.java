package com.fin.controller;

import com.fin.dto.*;
import com.fin.model.User;
import com.fin.service.AuthService;
import com.fin.service.JwtService;
import com.fin.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    AuthController(AuthService authService, JwtService jwtService){
        this.authService=authService;
        this.jwtService=jwtService;
    }

    @PostMapping("/token-health")
    public ResponseEntity<?> tokenHealth(@RequestBody TokenValidate tokenValidate){
        boolean isValid=jwtService.validateToken(tokenValidate.getToken(), tokenValidate.getUsername());
        System.out.println("Token Health: "+isValid);
        return isValid?ResponseEntity.status(HttpStatus.OK).body(true):
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> clientSignup(@RequestBody User user){

        ServiceResponse<Boolean> response = authService.registerUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signup/otp")
    public ResponseEntity<?> clientSignupOtp(@RequestBody OtpDto otpDto){

        ServiceResponse<UserPublicDataDto> response = authService.signupUser(otpDto);

        if(!response.getStatus()) return ResponseEntity.status(HttpStatus.OK).body(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> clientLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request){
        ServiceResponse response=authService.authenticate(userLoginDto);

        if(response.getStatus())return ResponseEntity.status(HttpStatus.OK).body(response);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
