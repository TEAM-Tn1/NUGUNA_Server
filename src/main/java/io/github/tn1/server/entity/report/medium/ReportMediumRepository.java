package io.github.tn1.server.entity.report.medium;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMediumRepository extends JpaRepository<ReportMedium, Long> {
}
