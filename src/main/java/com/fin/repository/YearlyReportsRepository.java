package com.fin.repository;

import com.fin.dto.YearlyReportPublicDto;
import com.fin.model.YearlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyReportsRepository extends JpaRepository<YearlyReports, Long> {

    @Query(value = "SELECT y_report_id, y_report_year, y_report_month, y_report_month_target " +
                    "FROM yearly_reports WHERE user_id = :userId ORDER BY y_report_year DESC, y_report_month ASC",nativeQuery = true)
    List<YearlyReportPublicDto> getYearlyReportOfUser(@Param("userId") int userId);
}
