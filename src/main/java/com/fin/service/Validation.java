package com.fin.service;

import org.springframework.stereotype.Service;

@Service
public final class Validation {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=!\\s]{6,}$";
    private static final String USERNAME_REGEX = "^[A-Za-z0-9._-]{3,30}$";
    private static final String DOUBLE_REGEX = "^-?(0|[1-9]\\d*)\\.\\d{1,2}$";
    private static final String DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    public boolean validateMonth(int month) {
        return month >= 1 && month <= 12;
    }

    public boolean validateYear(int year) {
        return year >= 1900 && year <= 2200;
    }

    public boolean validateDoubleAmount(String amount) {
        if (amount == null || amount.isBlank()) return false;
        return amount.trim().matches(DOUBLE_REGEX);
    }

    public boolean validateDate(String date) {
        if (date == null || date.isBlank()) return false;
        return date.trim().matches(DATE_REGEX);
    }

    public boolean validateEmail(String email) {
        if(email!=null && (email.isEmpty() || email.isBlank()))
            return false;
        return email.trim().matches(Validation.EMAIL_REGEX);
    }

    public boolean validatePassword(String pass) {
        if(pass!=null && (pass.isEmpty() || pass.isBlank()))
            return false;
        return pass.trim().matches(Validation.PASSWORD_REGEX);
    }

    public boolean validateUsername(String username) {
        if(username!=null && (username.isEmpty() || username.isBlank()))
            return false;
        return username.trim().matches(Validation.USERNAME_REGEX);
    }

    public boolean validateUserSignupDetails(String email, String pass) {
        return validateEmail(email) && validatePassword(pass);
    }
}
