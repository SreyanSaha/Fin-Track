package com.fin.repository;

import com.fin.dto.MonthlyReportPublicDto;
import com.fin.model.MonthlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonthlyReportsRepository extends JpaRepository<MonthlyReports, UUID> {
    @Query("""
    SELECT new com.fin.dto.MonthlyReportPublicDto(
        m.mReportId,
        m.mReportDate,
        m.mReportAmount,
        m.mReportNarration,
        y.yReportId
    )
    FROM MonthlyReports m
    JOIN m.yearlyReports y
    WHERE y.user.userId = :userId
      AND y.yReportYear = :yYear
      AND y.yReportMonth = :yMonth
    ORDER BY m.mReportDate DESC
    """)
    List<MonthlyReportPublicDto> getMonthlyRecordsOfUser(@Param("userId") int userId, @Param("yMonth") int yMonth,
                                                         @Param("yYear")  int yYear);

    @Query("""
    SELECT new com.fin.dto.MonthlyReportPublicDto(
        m.mReportId,
        m.mReportDate,
        m.mReportAmount,
        m.mReportNarration,
        y.yReportId
    )
    FROM MonthlyReports m
    JOIN m.yearlyReports y
    WHERE y.user.userId = :userId
      AND y.yReportYear = :yYear
      AND y.yReportMonth = :yMonth
    LIMIT 1
    """)
    Optional<MonthlyReportPublicDto> isMonthlyRecordsOfUserPresent(@Param("userId") int userId, @Param("yMonth") int yMonth,
                                                         @Param("yYear")  int yYear);

    @Query("""
    SELECT new com.fin.dto.MonthlyReportPublicDto(
        m.mReportId,
        m.mReportDate,
        m.mReportAmount,
        m.mReportNarration,
        y.yReportId
    )
    FROM MonthlyReports m
    JOIN m.yearlyReports y
    WHERE y.user.userId = :userId
      AND m.mReportId = :mReportId
    ORDER BY m.mReportDate DESC
    """)
    Optional<MonthlyReportPublicDto> getMonthlyRecordOfUser(@Param("userId") int userId, @Param("mReportId")  UUID mReportId);
}

