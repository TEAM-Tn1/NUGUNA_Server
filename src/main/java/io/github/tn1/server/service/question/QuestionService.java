package io.github.tn1.server.service.question;

import io.github.tn1.server.dto.question.request.QuestionRequest;
import io.github.tn1.server.entity.question.Question;
import io.github.tn1.server.entity.question.QuestionRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final UserFacade userFacade;
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;

	public void postQuestion(QuestionRequest request) {
		User user = userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		questionRepository.save(
				Question.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.user(user)
				.build()
		);
	}

}
