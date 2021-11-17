package io.github.tn1.server.domain.report.domain.repository;

import java.util.List;

import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.report.domain.types.ReportType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findByReportType(ReportType reportType, Pageable pageable);
}
