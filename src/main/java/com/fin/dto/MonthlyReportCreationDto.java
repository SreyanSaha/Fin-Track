package com.fin.dto;

import java.time.LocalDate;

public class MonthlyReportCreationDto {
    private String monthlyReportId, monthlyReportNarration;
    private double monthlyReportAmount;
    private Long yearlyReportId;
    private LocalDate monthlyReportDate;

    public String getMonthlyReportId() {
        return monthlyReportId;
    }
    public void setMonthlyReportId(String monthlyReportId) {
        this.monthlyReportId = monthlyReportId;
    }
    public String getMonthlyReportNarration() {
        return monthlyReportNarration;
    }
    public void setMonthlyReportNarration(String monthlyReportNarration) {
        this.monthlyReportNarration = monthlyReportNarration;
    }
    public double getMonthlyReportAmount() {
        return monthlyReportAmount;
    }
    public void setMonthlyReportAmount(double monthlyReportAmount) {
        this.monthlyReportAmount = monthlyReportAmount;
    }
    public Long getYearlyReportId() {
        return yearlyReportId;
    }
    public void setYearlyReportId(Long yearlyReportId) {
        this.yearlyReportId = yearlyReportId;
    }
    public LocalDate getMonthlyReportDate() {
        return monthlyReportDate;
    }
    public void setMonthlyReportDate(LocalDate monthlyReportDate) {
        this.monthlyReportDate = monthlyReportDate;
    }

}