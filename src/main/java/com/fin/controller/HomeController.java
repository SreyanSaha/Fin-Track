package com.fin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String getHomePage(){
        return "index.html";
    }

    @GetMapping("/login")
    public String getLoginSignupPage(){
        return "login_signup.html";
    }

    @GetMapping("/forget-password")
    public String getForgetPasswordPage(){
        return "forget_password.html";
    }

    @GetMapping("/data-migration")
    public String getDataMigrationPage(){
        return "data_migration.html";
    }
}
