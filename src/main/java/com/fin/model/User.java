package com.fin.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @Column(unique = true, nullable = false, name = "userEmail")
    private String userEmail;

    @Column(unique = true, nullable = false, name = "userName")
    private String userName;

    @Column(nullable = false, name = "userPassword")
    private String userPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<YearlyReports> yearlyReportsList;

    public List<YearlyReports> getYearlyReportsList() {
        return yearlyReportsList;
    }

    public void setYearlyReportsList(List<YearlyReports> yearlyReportsList) {
        this.yearlyReportsList = yearlyReportsList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
