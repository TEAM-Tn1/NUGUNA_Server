package io.github.tn1.server.controller;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.admin.request.ReportResultRequest;
import io.github.tn1.server.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.dto.admin.response.QuestionResponse;
import io.github.tn1.server.dto.admin.response.ReportInformationResponse;
import io.github.tn1.server.dto.admin.response.UserReportResponse;
import io.github.tn1.server.service.admin.AdminService;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/report/feed")
	public List<FeedReportResponse> queryFeedReport() {
		return adminService.queryFeedReport();
	}

	@GetMapping("/report/users")
	public List<UserReportResponse> queryUserReport() {
		return adminService.queryUserReport();
	}

	@GetMapping("/question")
	public List<QuestionResponse> queryQuestion() {
		return adminService.queryQuestion();
	}

	@GetMapping("/report/{report_id}")
	public ReportInformationResponse queryReportInformation(@PathVariable("report_id") Long reportId) {
		return adminService.queryReportInformation(reportId);
	}

	@GetMapping("/question/{question_id}")
	public QuestionInformationResponse queryQuestionInformation(@PathVariable("question_id") Long questionId) {
		return adminService.queryQuestionInformation(questionId);
	}

	@PostMapping("/report/feed")
	@ResponseStatus(HttpStatus.CREATED)
	public void feedReportResult(@RequestBody @Valid ReportResultRequest request) {
		adminService.feedReportResult(request);
	}

}
