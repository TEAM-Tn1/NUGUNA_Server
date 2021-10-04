package io.github.tn1.server.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.dto.admin.response.UserReportResponse;
import io.github.tn1.server.entity.report.ReportRepository;
import io.github.tn1.server.entity.report.ReportType;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final ReportRepository reportRepository;

	public List<FeedReportResponse> queryFeedReport() {
		return reportRepository.findByReportType(ReportType.F)
				.stream().map(report ->
					new FeedReportResponse(
							report.getId(),
							report.getTitle(),
							report.getReporter().getName(),
							report.getCreatedDate(),
							report.isCheck()
					)
		).collect(Collectors.toList());
	}

	public List<UserReportResponse> queryUserReport() {
		return reportRepository.findByReportType(ReportType.U)
				.stream().map(report ->
					new UserReportResponse(
							report.getId(),
							report.getTitle(),
							report.getReporter().getName(),
							report.getDefendant().getName(),
							report.getCreatedDate(),
							report.isCheck()
					)
				).collect(Collectors.toList());
	}

}
