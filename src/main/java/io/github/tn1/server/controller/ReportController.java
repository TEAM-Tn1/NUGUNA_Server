package io.github.tn1.server.controller;

import javax.validation.Valid;

import io.github.tn1.server.dto.report.request.UserReportRequest;
import io.github.tn1.server.dto.report.response.ReportResponse;
import io.github.tn1.server.service.report.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping("/users")
	public ReportResponse userReport(@RequestBody @Valid UserReportRequest request) {
		return reportService.userReport(request);
	}

}
