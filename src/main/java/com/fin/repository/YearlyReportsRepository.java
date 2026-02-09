package com.fin.repository;

import com.fin.model.YearlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyReportsRepository extends JpaRepository<YearlyReports, Long> {

}
