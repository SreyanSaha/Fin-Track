package com.fin.dto;

public class NotificationDto {

    private boolean status;
    private int progress;
    private String title;

    public NotificationDto(boolean status, int progress, String title) {
        this.status = status;
        this.progress = progress;
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public int getProgress() {
        return progress;
    }

    public String getTitle() {
        return title;
    }
}