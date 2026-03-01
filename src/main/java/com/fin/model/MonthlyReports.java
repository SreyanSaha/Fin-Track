package com.fin.model;

import com.fin.dto.MonthlyReportCreationDto;
import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "monthlyReports")
public class MonthlyReports {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID mReportId;

    @Column(nullable = false)
    private LocalDate mReportDate;

    @Column(nullable = false)
    private double mReportAmount;

    @Column(nullable = true)
    private String mReportNarration;

    @ManyToOne
    @JoinColumn(name = "y_report_id", nullable = false)
    private YearlyReports yearlyReports;

    public MonthlyReports(MonthlyReportCreationDto monthlyReportCreationDto, YearlyReports yearlyReports) {
        this.mReportDate =  monthlyReportCreationDto.getMonthlyReportDate();
        this.mReportAmount = monthlyReportCreationDto.getMonthlyReportAmount();
        this.mReportNarration = monthlyReportCreationDto.getMonthlyReportNarration();
        this.yearlyReports = yearlyReports;
    }

    public MonthlyReports() {
    }

    public UUID getMReportId() {
        return mReportId;
    }

    public void setMReportId(UUID mReportId) {
        this.mReportId = mReportId;
    }

    public LocalDate getMReportDate() {
        return mReportDate;
    }

    public void setMReportDate(LocalDate mReportDate) {
        this.mReportDate = mReportDate;
    }

    public double getMReportAmount() {
        return mReportAmount;
    }

    public void setMReportAmount(double mReportAmount) {
        this.mReportAmount = mReportAmount;
    }

    public String getMReportNarration() {
        return mReportNarration;
    }

    public void setMReportNarration(String mReportNarration) {
        this.mReportNarration = mReportNarration;
    }

    public YearlyReports getYearlyReports() {
        return yearlyReports;
    }

    public void setYearlyReports(YearlyReports yearlyReports) {
        this.yearlyReports = yearlyReports;
    }
}
