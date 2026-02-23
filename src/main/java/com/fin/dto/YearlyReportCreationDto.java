package com.fin.dto;

public class YearlyReportCreationDto {
    private int yReportYear;
    private int yReportMonth;
    private double yReportMonthTarget;

    public int getYReportYear() {
        return yReportYear;
    }

    public void setYReportYear(int yReportYear) {
        this.yReportYear = yReportYear;
    }

    public int getYReportMonth() {
        return yReportMonth;
    }

    public void setYReportMonth(short yReportMonth) {
        this.yReportMonth = yReportMonth;
    }

    public double getYReportMonthTarget() {
        return yReportMonthTarget;
    }

    public void setYReportMonthTarget(double yReportMonthTarget) {
        this.yReportMonthTarget = yReportMonthTarget;
    }
}
