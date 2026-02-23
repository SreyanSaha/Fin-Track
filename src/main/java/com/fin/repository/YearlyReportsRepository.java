package com.fin.repository;

import com.fin.dto.YearlyReportPublicDto;
import com.fin.model.YearlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearlyReportsRepository extends JpaRepository<YearlyReports, Long> {

    @Query(value = "SELECT y_report_id, y_report_year, y_report_month, y_report_month_target " +
                    "FROM yearly_reports WHERE user_id = :userId ORDER BY y_report_year DESC, y_report_month ASC",nativeQuery = true)
    List<YearlyReportPublicDto> getYearlyReportOfUser(@Param("userId") int userId);

    @Query(value = "SELECT COUNT(y_report_id) FROM yearly_reports " +
            "WHERE y_report_year = :yReportYear AND y_report_month = :yReportMonth AND user_id = :userId",nativeQuery = true)
    int isYearlyRecordPresent(@Param("yReportYear") int yReportYear, @Param("yReportMonth") int yReportMonth, @Param("userId") int userId);

    @Query(value = "SELECT y_report_id, y_report_year, y_report_month, y_report_month_target, user_id FROM yearly_reports " +
            "WHERE y_report_id = :yReportId AND user_id = :userId",nativeQuery = true)
    Optional<YearlyReports> findYearlyReport(@Param("yReportId") long yReportId, @Param("userId") int userId);
}
