package com.fin.service;

import com.fin.dto.*;
import com.fin.mail.MailText;
import com.fin.model.User;
import com.fin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final Validation validation;
    private final EmailService emailService;
    private final ConcurrentHashMap<String, RegistrationOtpDto> otpStorage = new
            ConcurrentHashMap<String, RegistrationOtpDto>();
    private final ConcurrentHashMap<String, User> userStorage = new
            ConcurrentHashMap<String, User>();
    private final MailText mailText=new MailText();
    private final AuthenticationManager authenticationManager;

    @Autowired
    AuthService(UserRepository userRepository, Validation validation, EmailService emailService, AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.emailService=emailService;
        this.validation=validation;
        this.authenticationManager=authenticationManager;
    }

    public ServiceResponse<Boolean> registerUser(User user){
        if(!validation.validateEmail(user.getUserEmail()))
            return new ServiceResponse<Boolean>("Invalid email.", false);
        if(!validation.validatePassword(user.getUserPassword()))
            return new ServiceResponse<Boolean>("Password is invalid. It must be at least 6 characters long and contain letters, numbers, and at least one special character.", false);
        if(userRepository.findByUserEmail(user.getUserEmail()).isPresent())
            return new ServiceResponse<Boolean>("Account already exists with this email.", false);

        userStorage.put(user.getUserEmail(), user);

        RegistrationOtpDto otp=new RegistrationOtpDto();

        otpStorage.put(user.getUserEmail(), otp);

        boolean response = emailService.sendEmail(user.getUserEmail().trim(), mailText.getClientOtpMailSubject(),
                mailText.getClientOtpMail().toString().replace("${otp}", otp.getOtp().toString()));

        return new ServiceResponse<Boolean>("Registration otp email sent.", response);
    }

    public ServiceResponse<UserPublicDataDto> signupUser(OtpDto otpDto) {
        RegistrationOtpDto otp=otpStorage.get(otpDto.getEmail());

        if(!otp.getGeneratedAt().plusMinutes(1).isAfter(LocalDateTime.now()))
            return new ServiceResponse<>("Otp expired.", false);
        if(!otp.getOtp().toString().equals(otpDto.getOtp()))
            return new ServiceResponse<>("Otp invalid.", false);

        otpStorage.remove(otpDto.getEmail());

        User user=userStorage.get(otpDto.getEmail());
        user.setUserName(user.getUserEmail().trim().substring(0, user.getUserEmail().trim().lastIndexOf('@')));
        user.setUserPassword(BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt(10)));

        User savedUser = userRepository.save(user);
        if(savedUser.getUserEmail()==null)
            return new ServiceResponse<>("User registration failed.", false);

       otpStorage.remove(otpDto.getEmail());
       userStorage.remove(otpDto.getEmail());

        emailService.sendEmail(user.getUserEmail(), mailText.getClientRegistrationMailSubject(),
                mailText.getClientRegistrationMail().toString().replace("${username}", user.getUserName()));

        return new ServiceResponse<UserPublicDataDto>("Registration Successful!\n"
                + "An email has been sent to you with further instructions. "
                + "Please check your inbox.", new UserPublicDataDto(savedUser), true);
    }

    public ServiceResponse<Boolean> loginUser(UserLoginDto userLoginDto) {

        return new ServiceResponse<>("", true);
    }
}
