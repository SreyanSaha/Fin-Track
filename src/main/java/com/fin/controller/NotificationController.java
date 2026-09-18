package com.fin.controller;

import com.fin.service.RequestStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final RequestStateManager requestStateManager;

    @Autowired
    NotificationController(RequestStateManager requestStateManager){
        this.requestStateManager=requestStateManager;
    }

    @GetMapping
    public ResponseEntity<?> getMonthlyDataExportDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(requestStateManager.getNotificationDetails());
    }
}
