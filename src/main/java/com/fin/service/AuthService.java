package com.fin.service;

import com.fin.dto.*;
import com.fin.mail.MailText;
import com.fin.model.User;
import com.fin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final Validation validation;
    private final EmailService emailService;
    private final ConcurrentHashMap<String, RegistrationOtpDto> otpStorage = new
            ConcurrentHashMap<String, RegistrationOtpDto>();
    private final ConcurrentHashMap<String, RegistrationOtpDto> forgetPasswordOtpStorage = new
            ConcurrentHashMap<String, RegistrationOtpDto>();
    private final ConcurrentHashMap<String, User> userStorage = new
            ConcurrentHashMap<String, User>();
    private final ConcurrentHashMap<String, User> forgetPasswordUserStorage = new
            ConcurrentHashMap<String, User>();
    private final MailText mailText=new MailText();
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    AuthService(UserRepository userRepository, Validation validation,
                EmailService emailService, PasswordEncoder passwordEncoder
            , AuthenticationManager authenticationManager, JwtService jwtService){
        this.userRepository=userRepository;
        this.emailService=emailService;
        this.validation=validation;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
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
                mailText.getClientOtpMail().replace("${otp}", otp.getOtp().toString()));

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
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        User savedUser = userRepository.save(user);
        if(savedUser.getUserEmail()==null)
            return new ServiceResponse<>("User registration failed.", false);

       otpStorage.remove(otpDto.getEmail());
       userStorage.remove(otpDto.getEmail());

        emailService.sendEmail(user.getUserEmail(), mailText.getClientRegistrationMailSubject(),
                mailText.getClientRegistrationMail().replace("${username}", user.getUserName()));

        return new ServiceResponse<UserPublicDataDto>("Registration Successful!\n"
                + "An email has been sent to you with further instructions. "
                + "Please check your inbox.", new UserPublicDataDto(savedUser), true);
    }

    public ServiceResponse<Boolean> sendForgetPasswordOtpMail(String email){
        if(!validation.validateEmail(email))
            return new ServiceResponse<>("Invalid email.", false);
        Optional<User> user=userRepository.findByUserEmail(email);
        if(user.isEmpty())
            return new ServiceResponse<Boolean>("No account is associated with this email.", false);

        forgetPasswordUserStorage.put(user.get().getUserEmail(), user.get());

        RegistrationOtpDto otp=new RegistrationOtpDto();

        forgetPasswordOtpStorage.put(user.get().getUserEmail(), otp);

        boolean response = emailService.sendEmail(user.get().getUserEmail().trim(), mailText.getForgotPasswordOtpMailSubject(),
                mailText.getForgotPasswordOtpMail().replace("${otp}", otp.getOtp().toString()));

        return new ServiceResponse<Boolean>("Password Reset otp email sent.", response);
    }

//    public ServiceResponse<Boolean> loginUser(UserLoginDto userLoginDto, HttpServletRequest request) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                    userLoginDto.getUsername(),
//                                    userLoginDto.getUserPassword()
//                            )
//                    );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println(authentication.isAuthenticated());
//            return new ServiceResponse<>("Login successful", true);
//        } catch (AuthenticationException e) {
//            return new ServiceResponse<>("Invalid email or password", false);
//        }
//    }

    public ServiceResponse<AuthResponse> authenticate(UserLoginDto userLoginDto) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getUsername(),
                            userLoginDto.getUserPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                AuthResponse authResponse = new AuthResponse(
                        jwtService.generateToken(userLoginDto.getUsername()),
                        userLoginDto.getUsername()
                );

                return new ServiceResponse<>("Login successful", authResponse, true);
            }

        } catch (AuthenticationException exception) {
            System.out.println(exception.getMessage());
            return new ServiceResponse<>("Invalid username or password", false);
        }

        return new ServiceResponse<>("Authentication failed", false);
    }
}
