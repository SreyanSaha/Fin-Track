package com.fin.dto;

import java.time.Year;

public class YearlyReportCreationDto {
    private int yReportYear;
    private short yReportMonth;
    private double yReportMonthTarget;

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
