package com.fin.dto;

import com.fin.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestStateDto {
    private User user;
    private boolean monthlyDataExporting=false, yearlyDataExporting=false, monthlyBackupExporting=false,
            monthlyBackupImporting=false, yearlyBackupExporting=false, yearlyBackupImporting=false, isLoggedIn=false,
            canWrite=true, signupOtpReceived=false, forgetPasswordOtpReceived=false;
    private LocalDateTime signupOtpExpiry=null, forgetPasswordOtpExpiry=null;
    private int monthlyDataExportingProgress=0, yearlyDataExportingProgress=0,
            monthlyBackupExportingProgress=0, yearlyBackupExportingProgress=0,
            monthlyBackupImportingProgress=0, yearlyBackupImportingProgress=0;
    private String monthlyDataExportingTitle="", yearlyDataExportingTitle="", monthlyBackupExportingTitle="",
            monthlyBackupImportingTitle="", yearlyBackupExportingTitle="", yearlyBackupImportingTitle="";

    public String getMonthlyDataExportingTitle() {
        return monthlyDataExportingTitle;
    }

    public void setMonthlyDataExportingTitle(String monthlyDataExportingTitle) {
        this.monthlyDataExportingTitle = monthlyDataExportingTitle;
    }

    public String getYearlyDataExportingTitle() {
        return yearlyDataExportingTitle;
    }

    public void setYearlyDataExportingTitle(String yearlyDataExportingTitle) {
        this.yearlyDataExportingTitle = yearlyDataExportingTitle;
    }

    public String getMonthlyBackupExportingTitle() {
        return monthlyBackupExportingTitle;
    }

    public void setMonthlyBackupExportingTitle(String monthlyBackupExportingTitle) {
        this.monthlyBackupExportingTitle = monthlyBackupExportingTitle;
    }

    public String getMonthlyBackupImportingTitle() {
        return monthlyBackupImportingTitle;
    }

    public void setMonthlyBackupImportingTitle(String monthlyBackupImportingTitle) {
        this.monthlyBackupImportingTitle = monthlyBackupImportingTitle;
    }

    public String getYearlyBackupExportingTitle() {
        return yearlyBackupExportingTitle;
    }

    public void setYearlyBackupExportingTitle(String yearlyBackupExportingTitle) {
        this.yearlyBackupExportingTitle = yearlyBackupExportingTitle;
    }

    public String getYearlyBackupImportingTitle() {
        return yearlyBackupImportingTitle;
    }

    public void setYearlyBackupImportingTitle(String yearlyBackupImportingTitle) {
        this.yearlyBackupImportingTitle = yearlyBackupImportingTitle;
    }

    public int getMonthlyDataExportingProgress() {
        return monthlyDataExportingProgress;
    }

    public void setMonthlyDataExportingProgress(int monthlyDataExportingProgress) {
        this.monthlyDataExportingProgress = monthlyDataExportingProgress;
    }

    public int getYearlyDataExportingProgress() {
        return yearlyDataExportingProgress;
    }

    public void setYearlyDataExportingProgress(int yearlyDataExportingProgress) {
        this.yearlyDataExportingProgress = yearlyDataExportingProgress;
    }

    public int getMonthlyBackupExportingProgress() {
        return monthlyBackupExportingProgress;
    }

    public void setMonthlyBackupExportingProgress(int monthlyBackupExportingProgress) {
        this.monthlyBackupExportingProgress = monthlyBackupExportingProgress;
    }

    public int getYearlyBackupExportingProgress() {
        return yearlyBackupExportingProgress;
    }

    public void setYearlyBackupExportingProgress(int yearlyBackupExportingProgress) {
        this.yearlyBackupExportingProgress = yearlyBackupExportingProgress;
    }

    public int getMonthlyBackupImportingProgress() {
        return monthlyBackupImportingProgress;
    }

    public void setMonthlyBackupImportingProgress(int monthlyBackupImportingProgress) {
        this.monthlyBackupImportingProgress = monthlyBackupImportingProgress;
    }

    public int getYearlyBackupImportingProgress() {
        return yearlyBackupImportingProgress;
    }

    public void setYearlyBackupImportingProgress(int yearlyBackupImportingProgress) {
        this.yearlyBackupImportingProgress = yearlyBackupImportingProgress;
    }

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
        this.monthlyDataExportingTitle = monthlyDataExporting?
                "Exporting monthly data and generating Excel file":
                "Monthly data has been exported successfully and the Excel file is ready.";
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

    public List<NotificationDto> getNotifications() {

        List<NotificationDto> notifications = new ArrayList<>();

        notifications.add(
                new NotificationDto(
                        this.monthlyDataExporting,
                        this.monthlyDataExportingProgress,
                        this.monthlyDataExportingTitle
                )
        );

        notifications.add(
                new NotificationDto(
                        this.yearlyDataExporting,
                        this.yearlyDataExportingProgress,
                        this.yearlyDataExportingTitle
                )
        );

        notifications.add(
                new NotificationDto(
                        this.monthlyBackupExporting,
                        this.monthlyBackupExportingProgress,
                        this.monthlyBackupExportingTitle
                )
        );

        notifications.add(
                new NotificationDto(
                        this.monthlyBackupImporting,
                        this.monthlyBackupImportingProgress,
                        this.monthlyBackupImportingTitle
                )
        );

        notifications.add(
                new NotificationDto(
                        this.yearlyBackupExporting,
                        this.yearlyBackupExportingProgress,
                        this.yearlyBackupExportingTitle
                )
        );

        notifications.add(
                new NotificationDto(
                        this.yearlyBackupImporting,
                        this.yearlyBackupImportingProgress,
                        this.yearlyBackupImportingTitle
                )
        );

        return notifications;
    }
}
