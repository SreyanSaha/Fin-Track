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
    private short yReportMonth;

    @Column(nullable = false, name = "yReportMonthTarget")
    private double yReportMonthTarget;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "yearlyReports", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MonthlyReports> monthlyReportsList;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
