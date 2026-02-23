package com.fin.model;

import jakarta.persistence.*;
import java.time.Year;
import java.util.List;

@Entity
@Table(name = "yearlyReports")
public class YearlyReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yReportId")
    private Long yReportId;

    @Column(nullable = false, name = "yReportYear")
    private int yReportYear;

    @Column(nullable = false, name = "yReportMonth")
    private int yReportMonth;

    @Column(nullable = false, name = "yReportMonthTarget")
    private double yReportMonthTarget;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "yearlyReports", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlyReports> monthlyReportsList;

    public YearlyReports(Long yReportId, int yReportYear, int yReportMonth,
                         double yReportMonthTarget, User user, List<MonthlyReports> monthlyReportsList) {
        this.yReportId = yReportId;
        this.yReportYear = yReportYear;
        this.yReportMonth = yReportMonth;
        this.yReportMonthTarget = yReportMonthTarget;
        this.user = user;
        this.monthlyReportsList = monthlyReportsList;
    }
    public YearlyReports(){}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
