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
    private final ConcurrentHashMap<String, ForgetPasswordOtpDto> forgetPasswordOtpStorage = new
            ConcurrentHashMap<String, ForgetPasswordOtpDto>();
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

        ForgetPasswordOtpDto otp=new ForgetPasswordOtpDto();

        forgetPasswordOtpStorage.put(user.get().getUserEmail(), otp);

        boolean response = emailService.sendEmail(user.get().getUserEmail().trim(), mailText.getForgotPasswordOtpMailSubject(),
                mailText.getForgotPasswordOtpMail().replace("${otp}", otp.getOtp().toString()));

        return new ServiceResponse<Boolean>("Password Reset otp email sent.", response);
    }

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

    public ServiceResponse<Boolean> verifyOtp(OtpDto otpDto) {
        if(!forgetPasswordOtpStorage.containsKey(otpDto.getEmail()))
            return new ServiceResponse<>("Otp invalid.", false);

        ForgetPasswordOtpDto otp=forgetPasswordOtpStorage.get(otpDto.getEmail());

        if(!otp.getGeneratedAt().plusMinutes(1).isAfter(LocalDateTime.now()))
            return new ServiceResponse<>("Otp expired.", false);
        if(!otp.getOtp().toString().equals(otpDto.getOtp()))
            return new ServiceResponse<>("Otp invalid.", false);

        otp.setVerified(true);
        forgetPasswordOtpStorage.put(otpDto.getEmail(), otp);

        return new ServiceResponse<>("OTP verified successfully. Please enter your new password.", true);
    }

    public ServiceResponse<Boolean> resetPassword(ResetPasswordDto resetPasswordDto) {
        if(!validation.validateEmail(resetPasswordDto.getEmail()) ||
                !forgetPasswordOtpStorage.containsKey(resetPasswordDto.getEmail()) ||
                !forgetPasswordUserStorage.containsKey(resetPasswordDto.getEmail()))
            return new ServiceResponse<Boolean>("Invalid email.", false);
        if(!validation.validatePassword(resetPasswordDto.getPassword()))
            return new ServiceResponse<Boolean>("Password is invalid. It must be at least 6 characters long and contain letters, numbers, and at least one special character.", false);
        if(!forgetPasswordOtpStorage.get(resetPasswordDto.getEmail()).isVerified())
            return new ServiceResponse<Boolean>("Otp not verified.", false);

        User user=forgetPasswordUserStorage.get(resetPasswordDto.getEmail());
        user.setUserPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        User savedUser=userRepository.save(user);

        forgetPasswordOtpStorage.remove(resetPasswordDto.getEmail());
        forgetPasswordUserStorage.remove(resetPasswordDto.getEmail());

        return savedUser.getUserEmail()==null?
                new ServiceResponse<>("Failed to reset password.", false):
                new ServiceResponse<>("Password reset successful.", true);
    }
}
