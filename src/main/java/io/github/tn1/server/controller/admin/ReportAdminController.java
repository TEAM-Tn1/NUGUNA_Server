package io.github.tn1.server.controller.admin;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.admin.request.FeedReportResultRequest;
import io.github.tn1.server.dto.admin.request.UserReportResultRequest;
import io.github.tn1.server.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.dto.admin.response.ReportInformationResponse;
import io.github.tn1.server.dto.admin.response.UserReportResponse;
import io.github.tn1.server.service.admin.ReportAdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/report")
@RequiredArgsConstructor
public class ReportAdminController {

	private final ReportAdminService adminService;

	@GetMapping("/feed")
	public List<FeedReportResponse> queryFeedReport() {
		return adminService.queryFeedReport();
	}

	@GetMapping("/users")
	public List<UserReportResponse> queryUserReport() {
		return adminService.queryUserReport();
	}

	@GetMapping("/{report_id}")
	public ReportInformationResponse queryReportInformation(@PathVariable("report_id") Long reportId) {
		return adminService.queryReportInformation(reportId);
	}

	@PostMapping("/feed")
	@ResponseStatus(HttpStatus.CREATED)
	public void feedReportResult(@RequestBody @Valid FeedReportResultRequest request) {
		adminService.feedReportResult(request);
	}

	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public void userReportResult(@RequestBody @Valid UserReportResultRequest request) {
		adminService.userReportResult(request);
	}

}
