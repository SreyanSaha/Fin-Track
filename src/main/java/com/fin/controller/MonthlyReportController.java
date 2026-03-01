package com.fin.controller;

import com.fin.dto.MonthlyReportCreationDto;
import com.fin.dto.MonthlyReportFetchDto;
import com.fin.dto.MonthlyReportPublicDto;
import com.fin.dto.ServiceResponse;
import com.fin.model.MonthlyReports;
import com.fin.service.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monthly")
public class MonthlyReportController {
    private final MonthlyReportService monthlyReportService;

    @Autowired
    MonthlyReportController(MonthlyReportService monthlyReportService){
        this.monthlyReportService = monthlyReportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getMonthlyReport(MonthlyReportFetchDto monthlyReportFetchDto){
        ServiceResponse<MonthlyReportPublicDto> response = monthlyReportService.getMonthlyRecordsOfUser(monthlyReportFetchDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create-report")
    public ResponseEntity<?> createMonthlyReport(@RequestBody MonthlyReportCreationDto monthlyReportCreationDto){
        ServiceResponse<Boolean> response = monthlyReportService.createMonthlyReport(monthlyReportCreationDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
