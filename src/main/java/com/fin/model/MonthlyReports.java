package com.fin.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "monthlyReports")
public class MonthlyReports {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID mReportId;

    @Column(nullable = false)
    private Date mReportDate;

    @Column(nullable = false)
    private double mReportAmount;

    @Column(nullable = true)
    private String mReportNarration;

    @ManyToOne
    @JoinColumn(name = "y_report_id", nullable = false)
    private YearlyReports yearlyReports;

    public UUID getmReportId() {
        return mReportId;
    }

    public void setmReportId(UUID mReportId) {
        this.mReportId = mReportId;
    }

    public Date getmReportDate() {
        return mReportDate;
    }

    public void setmReportDate(Date mReportDate) {
        this.mReportDate = mReportDate;
    }

    public double getmReportAmount() {
        return mReportAmount;
    }

    public void setmReportAmount(double mReportAmount) {
        this.mReportAmount = mReportAmount;
    }

    public String getmReportNarration() {
        return mReportNarration;
    }

    public void setmReportNarration(String mReportNarration) {
        this.mReportNarration = mReportNarration;
    }

    public YearlyReports getYearlyReports() {
        return yearlyReports;
    }

    public void setYearlyReports(YearlyReports yearlyReports) {
        this.yearlyReports = yearlyReports;
    }
}
