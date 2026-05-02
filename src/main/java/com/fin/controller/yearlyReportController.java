package com.fin.controller;

import com.fin.dto.ExportYearlyBackupDto;
import com.fin.dto.ServiceResponse;
import com.fin.dto.YearlyReportCreationDto;
import com.fin.dto.YearlyReportPublicDto;
import com.fin.service.YearlyReportService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create-report")
    public ResponseEntity<?> createYearlyReport(@RequestBody YearlyReportCreationDto yearlyReportCreationDto){
        ServiceResponse<Boolean> response = yearlyReportService.createYearlyReport(yearlyReportCreationDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete-report")
    public ResponseEntity<?> deleteYearlyReport(@RequestParam long yearlyReportId){
        ServiceResponse<Boolean> response = yearlyReportService.deleteYearlyReport(yearlyReportId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/export-backup")
    public ResponseEntity<?> exportYearlyReportBackup(@RequestBody ExportYearlyBackupDto exportYearlyBackupDto){
        ServiceResponse<Boolean> response = yearlyReportService.exportBackup(exportYearlyBackupDto.getYear());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
