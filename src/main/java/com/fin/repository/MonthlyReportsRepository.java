package com.fin.repository;

import com.fin.model.MonthlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyReportsRepository extends JpaRepository<MonthlyReports, String> {
}
