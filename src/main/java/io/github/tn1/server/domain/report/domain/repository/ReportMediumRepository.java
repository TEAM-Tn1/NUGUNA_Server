package io.github.tn1.server.domain.report.domain.repository;

import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.report.domain.ReportMedium;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMediumRepository extends JpaRepository<ReportMedium, Long> {
	boolean existsByReport(Report report);
}
