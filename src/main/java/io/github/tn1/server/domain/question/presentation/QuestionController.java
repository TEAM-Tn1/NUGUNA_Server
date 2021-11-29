package io.github.tn1.server.domain.question.presentation;

import javax.validation.Valid;

import io.github.tn1.server.domain.question.presentation.dto.request.QuestionRequest;
import io.github.tn1.server.domain.question.presentation.dto.response.QuestionResponse;
import io.github.tn1.server.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void postQuestion(@RequestBody @Valid QuestionRequest request) {
		questionService.postQuestion(request);
	}

	@GetMapping
	public QuestionResponse queryQuestionDetail(@RequestParam("question_id") Long questionId) {
		return questionService.queryQuestionDetail(questionId);
	}

}
