package com.fin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(){
        return "index.html";
    }

    @GetMapping("/login")
    public String getLoginSignupPage(){
        return "login_signup.html";
    }
}
