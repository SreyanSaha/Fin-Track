package com.fin.dto;

public class YearlyReportFetchDto extends YearlyReportCreationDto{
    public YearlyReportFetchDto(int yReportMonth, int yReportYear, double yReportMonthTarget){
        super(yReportYear, yReportMonth, yReportMonthTarget);
    }
}
