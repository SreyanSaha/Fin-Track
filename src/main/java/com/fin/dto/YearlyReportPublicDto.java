package com.fin.dto;

public class YearlyReportPublicDto {
    private Long yReportId;
    private int yReportYear;
    private int yReportMonth;
    private double yReportMonthTarget;

    public YearlyReportPublicDto(Long yReportId, int yReportYear, int yReportMonth, double yReportMonthTarget) {
        this.yReportId = yReportId;
        this.yReportYear = yReportYear;
        this.yReportMonth = yReportMonth;
        this.yReportMonthTarget = yReportMonthTarget;
    }

    public YearlyReportPublicDto() {
    }

    public Long getYReportId() {
        return yReportId;
    }

    public void setYReportId(Long yReportId) {
        this.yReportId = yReportId;
    }

    public int getYReportYear() {
        return yReportYear;
    }

    public void setYReportYear(int yReportYear) {
        this.yReportYear = yReportYear;
    }

    public int getYReportMonth() {
        return yReportMonth;
    }

    public void setYReportMonth(int yReportMonth) {
        this.yReportMonth = yReportMonth;
    }

    public double getYReportMonthTarget() {
        return yReportMonthTarget;
    }

    public void setYReportMonthTarget(double yReportMonthTarget) {
        this.yReportMonthTarget = yReportMonthTarget;
    }
}
