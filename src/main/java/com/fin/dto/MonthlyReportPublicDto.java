package com.fin.dto;

import java.time.LocalDate;
import java.util.UUID;

public class MonthlyReportPublicDto {
    private String mReportId;
    private LocalDate mReportDate;
    private Double mReportAmount;
    private String mReportNarration;
    private Long yReportId;

    public MonthlyReportPublicDto(UUID mReportId,
                                  LocalDate mReportDate,
                                  Double mReportAmount,
                                  String mReportNarration,
                                  Long yReportId) {
        this.mReportId = mReportId.toString();
        this.mReportDate = mReportDate;
        this.mReportAmount = mReportAmount;
        this.mReportNarration = mReportNarration;
        this.yReportId=yReportId;
    }

    public String getMReportId() {
        return mReportId;
    }

    public void setMReportId(String mReportId) {
        this.mReportId = mReportId;
    }

    public LocalDate getMReportDate() {
        return mReportDate;
    }

    public void setMReportDate(LocalDate mReportDate) {
        this.mReportDate = mReportDate;
    }

    public Double getMReportAmount() {
        return mReportAmount;
    }

    public void setMReportAmount(Double mReportAmount) {
        this.mReportAmount = mReportAmount;
    }

    public String getMReportNarration() {
        return mReportNarration;
    }

    public void setMReportNarration(String mReportNarration) {
        this.mReportNarration = mReportNarration;
    }

    public Long getYReportId() {
        return yReportId;
    }

    public void setyReportId(Long yReportId) {
        this.yReportId = yReportId;
    }
}
