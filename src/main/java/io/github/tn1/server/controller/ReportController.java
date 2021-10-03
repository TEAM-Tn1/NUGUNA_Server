package io.github.tn1.server.controller;

import javax.validation.Valid;

import io.github.tn1.server.dto.report.request.FeedReportRequest;
import io.github.tn1.server.dto.report.request.UserReportRequest;
import io.github.tn1.server.dto.report.response.ReportResponse;
import io.github.tn1.server.service.report.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping("/users")
	public ReportResponse userReport(@RequestBody @Valid UserReportRequest request) {
		return reportService.userReport(request);
	}

	@PostMapping("/feed")
	public ReportResponse feedReport(@RequestBody @Valid FeedReportRequest request) {
		return reportService.feedReport(request);
	}

	@PostMapping("/{report_id}/medium")
	@ResponseStatus(HttpStatus.CREATED)
	public void postMedium(@RequestPart MultipartFile file, @PathVariable("report_id") Long reportId) {
		reportService.postMedium(file, reportId);
	}

}
