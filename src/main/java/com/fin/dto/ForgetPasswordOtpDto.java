package com.fin.dto;

public class ForgetPasswordOtpDto extends RegistrationOtpDto{
    private boolean isVerified;

    public ForgetPasswordOtpDto() {
        super();
        this.isVerified=false;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
