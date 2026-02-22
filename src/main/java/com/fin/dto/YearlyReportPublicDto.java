package com.fin.dto;

public class YearlyReportPublicDto {
    private Long yReportId;
    private int yReportYear;
    private short yReportMonth;
    private double yReportMonthTarget;

    public Long getyReportId() {
        return yReportId;
    }

    public void setyReportId(Long yReportId) {
        this.yReportId = yReportId;
    }

    public int getyReportYear() {
        return yReportYear;
    }

    public void setyReportYear(int yReportYear) {
        this.yReportYear = yReportYear;
    }

    public short getyReportMonth() {
        return yReportMonth;
    }

    public void setyReportMonth(short yReportMonth) {
        this.yReportMonth = yReportMonth;
    }

    public double getyReportMonthTarget() {
        return yReportMonthTarget;
    }

    public void setyReportMonthTarget(double yReportMonthTarget) {
        this.yReportMonthTarget = yReportMonthTarget;
    }
}
