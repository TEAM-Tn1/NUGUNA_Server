package io.github.tn1.server.domain.question.service;

import io.github.tn1.server.domain.question.domain.Question;
import io.github.tn1.server.domain.question.domain.repository.QuestionRepository;
import io.github.tn1.server.domain.question.exception.NotYourQuestionException;
import io.github.tn1.server.domain.question.exception.QuestionNotFoundException;
import io.github.tn1.server.domain.question.exception.QuestionResultNotFoundException;
import io.github.tn1.server.domain.question.presentation.dto.request.QuestionRequest;
import io.github.tn1.server.domain.question.presentation.dto.response.QuestionResponse;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final UserFacade userFacade;
	private final QuestionRepository questionRepository;

	public void postQuestion(QuestionRequest request) {
		User user = userFacade.getCurrentUser();

		questionRepository.save(
				Question.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.user(user)
				.build()
		);
	}

	public QuestionResponse queryQuestionDetail(Long questionId) {
		Question question = questionRepository
				.findById(questionId)
				.orElseThrow(QuestionNotFoundException::new);

		if(!question.isOwner(userFacade.getCurrentEmail()))
			throw new NotYourQuestionException();

		if(question.getQuestionResult() == null)
			throw new QuestionResultNotFoundException();

		return new QuestionResponse(question
				.getQuestionResult().getReason());
	}

}
