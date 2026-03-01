package com.fin.dto;

public class MonthlyReportFetchDto extends YearlyReportCreationDto{
    public MonthlyReportFetchDto(int yReportMonth, int yReportYear, double yReportMonthTarget){
        super(yReportYear, yReportMonth, yReportMonthTarget);
    }
}
