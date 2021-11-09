package io.github.tn1.server.service.question;

import io.github.tn1.server.dto.question.request.QuestionDetailRequest;
import io.github.tn1.server.dto.question.request.QuestionRequest;
import io.github.tn1.server.dto.question.response.QuestionResponse;
import io.github.tn1.server.entity.question.Question;
import io.github.tn1.server.entity.question.QuestionRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.exception.NotYourQuestionException;
import io.github.tn1.server.exception.QuestionNotFoundException;
import io.github.tn1.server.exception.QuestionResultNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
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

	public QuestionResponse queryQuestionDetail(QuestionDetailRequest request) {
		Question question = questionRepository
				.findById(request.getQuestionId())
				.orElseThrow(QuestionNotFoundException::new);

		if(!question.isOwner(userFacade.getCurrentEmail()))
			throw new NotYourQuestionException();

		if(question.getQuestionResult() == null)
			throw new QuestionResultNotFoundException();

		return new QuestionResponse(question
				.getQuestionResult().getReason());
	}

}
