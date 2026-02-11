package com.fin.dto;

import com.fin.model.User;

public class UserPublicDataDto {
    private String userEmail, username;

    public UserPublicDataDto(User user) {
        this.userEmail=user.getUserEmail();
        this.username=user.getUserName();
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


}
