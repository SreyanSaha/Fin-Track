package com.fin.dto;

import com.fin.model.User;
import java.time.LocalDateTime;

public class RequestStateDto {
    private User user;
    private boolean monthlyDataExporting=false, yearlyDataExporting=false, monthlyBackupExporting=false,
            monthlyBackupImporting=false, yearlyBackupExporting=false, yearlyBackupImporting=false, isLoggedIn=false,
            canWrite=true, signupOtpReceived=false, forgetPasswordOtpReceived=false;
    private LocalDateTime signupOtpExpiry=null, forgetPasswordOtpExpiry=null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMonthlyDataExporting() {
        return monthlyDataExporting;
    }

    public void setMonthlyDataExporting(boolean monthlyDataExporting) {
        this.monthlyDataExporting = monthlyDataExporting;
    }

    public boolean isYearlyDataExporting() {
        return yearlyDataExporting;
    }

    public void setYearlyDataExporting(boolean yearlyDataExporting) {
        this.yearlyDataExporting = yearlyDataExporting;
    }

    public boolean isMonthlyBackupExporting() {
        return monthlyBackupExporting;
    }

    public void setMonthlyBackupExporting(boolean monthlyBackupExporting) {
        this.monthlyBackupExporting = monthlyBackupExporting;
    }

    public boolean isMonthlyBackupImporting() {
        return monthlyBackupImporting;
    }

    public void setMonthlyBackupImporting(boolean monthlyBackupImporting) {
        this.monthlyBackupImporting = monthlyBackupImporting;
    }

    public boolean isYearlyBackupExporting() {
        return yearlyBackupExporting;
    }

    public void setYearlyBackupExporting(boolean yearlyBackupExporting) {
        this.yearlyBackupExporting = yearlyBackupExporting;
    }

    public boolean isYearlyBackupImporting() {
        return yearlyBackupImporting;
    }

    public void setYearlyBackupImporting(boolean yearlyBackupImporting) {
        this.yearlyBackupImporting = yearlyBackupImporting;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public boolean isSignupOtpReceived() {
        return signupOtpReceived;
    }

    public void setSignupOtpReceived(boolean signupOtpReceived) {
        this.signupOtpReceived = signupOtpReceived;
    }

    public boolean isForgetPasswordOtpReceived() {
        return forgetPasswordOtpReceived;
    }

    public void setForgetPasswordOtpReceived(boolean forgetPasswordOtpReceived) {
        this.forgetPasswordOtpReceived = forgetPasswordOtpReceived;
    }

    public LocalDateTime getSignupOtpExpiry() {
        return signupOtpExpiry;
    }

    public void setSignupOtpExpiry(LocalDateTime signupOtpExpiry) {
        this.signupOtpExpiry = signupOtpExpiry;
    }

    public LocalDateTime getForgetPasswordOtpExpiry() {
        return forgetPasswordOtpExpiry;
    }

    public void setForgetPasswordOtpExpiry(LocalDateTime forgetPasswordOtpExpiry) {
        this.forgetPasswordOtpExpiry = forgetPasswordOtpExpiry;
    }
}
