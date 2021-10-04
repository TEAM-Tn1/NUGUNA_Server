package io.github.tn1.server.controller;

import javax.validation.Valid;

import io.github.tn1.server.dto.question.request.QuestionDetailRequest;
import io.github.tn1.server.dto.question.request.QuestionRequest;
import io.github.tn1.server.dto.question.response.QuestionResponse;
import io.github.tn1.server.service.question.QuestionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public QuestionResponse queryQuestionDetail(@RequestBody QuestionDetailRequest request) {
		return questionService.queryQuestionDetail(request);
	}

}
