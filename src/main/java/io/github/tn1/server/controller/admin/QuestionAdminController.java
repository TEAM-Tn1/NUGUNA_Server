package io.github.tn1.server.controller.admin;

import java.util.List;

import io.github.tn1.server.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.dto.admin.response.QuestionResponse;
import io.github.tn1.server.service.admin.QuestionAdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/question")
@RequiredArgsConstructor
public class QuestionAdminController {

	private QuestionAdminService adminService;

	@GetMapping
	public List<QuestionResponse> queryQuestion() {
		return adminService.queryQuestion();
	}

	@GetMapping("/{question_id}")
	public QuestionInformationResponse queryQuestionInformation(@PathVariable("question_id") Long questionId) {
		return adminService.queryQuestionInformation(questionId);
	}



}
