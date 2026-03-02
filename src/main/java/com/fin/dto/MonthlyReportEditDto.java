package com.fin.dto;

import java.time.LocalDate;

public class MonthlyReportEditDto extends MonthlyReportCreationDto{
    public MonthlyReportEditDto(String monthlyReportId, String monthlyReportNarration,
                         double monthlyReportAmount, Long yearlyReportId, LocalDate monthlyReportDate){
        super(monthlyReportId, monthlyReportNarration, monthlyReportAmount, yearlyReportId, monthlyReportDate);
    }
}
