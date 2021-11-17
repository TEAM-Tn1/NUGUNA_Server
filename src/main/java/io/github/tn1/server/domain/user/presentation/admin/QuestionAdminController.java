package io.github.tn1.server.domain.user.presentation.admin;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.domain.user.presentation.dto.admin.request.QuestionResultRequest;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.QuestionResponse;
import io.github.tn1.server.domain.user.service.admin.QuestionAdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/question")
@RequiredArgsConstructor
public class QuestionAdminController {

	private final QuestionAdminService adminService;

	@GetMapping
	public List<QuestionResponse> queryQuestion(Pageable pageable) {
		return adminService.queryQuestion(pageable);
	}

	@GetMapping("/{question_id}")
	public QuestionInformationResponse queryQuestionInformation(@PathVariable("question_id") Long questionId) {
		return adminService.queryQuestionInformation(questionId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void questionResult(@RequestBody @Valid QuestionResultRequest request) {
		adminService.questionResult(request);
	}



}
