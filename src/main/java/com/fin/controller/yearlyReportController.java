package com.fin.controller;

import com.fin.dto.ServiceResponse;
import com.fin.dto.YearlyReportPublicDto;
import com.fin.service.YearlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yearly")
public class yearlyReportController {
    private final YearlyReportService yearlyReportService;

    @Autowired
    yearlyReportController(YearlyReportService yearlyReportService){
        this.yearlyReportService=yearlyReportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getYearlyReportOfUser(){
        ServiceResponse<YearlyReportPublicDto> response = yearlyReportService.getYearlyReportOfUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
