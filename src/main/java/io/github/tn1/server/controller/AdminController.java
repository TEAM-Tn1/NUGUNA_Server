package io.github.tn1.server.controller;

import java.util.List;

import io.github.tn1.server.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.service.admin.AdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/feed")
	public List<FeedReportResponse> queryFeedReport() {
		return adminService.queryFeedReport();
	}

}
