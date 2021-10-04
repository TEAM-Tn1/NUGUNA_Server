package io.github.tn1.server.entity.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findByReportType(ReportType reportType);
}
