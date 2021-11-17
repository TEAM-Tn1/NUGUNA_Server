package io.github.tn1.server.domain.report.domain.repository;

import io.github.tn1.server.domain.report.domain.FeedReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedReportRepository extends JpaRepository<FeedReport, Long> {
}
