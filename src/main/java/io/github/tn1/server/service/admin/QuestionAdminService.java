package io.github.tn1.server.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.dto.admin.request.QuestionResultRequest;
import io.github.tn1.server.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.dto.admin.response.QuestionResponse;
import io.github.tn1.server.entity.question.Question;
import io.github.tn1.server.entity.question.QuestionRepository;
import io.github.tn1.server.entity.question.result.QuestionResult;
import io.github.tn1.server.entity.question.result.QuestionResultRepository;
import io.github.tn1.server.exception.AlreadyResultQuestionException;
import io.github.tn1.server.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionAdminService {

	private final QuestionRepository questionRepository;
	private final QuestionResultRepository questionResultRepository;

	public List<QuestionResponse> queryQuestion() {
		return questionRepository.findAll()
				.stream().map(question ->
						new QuestionResponse(
								question.getId(),
								question.getTitle(),
								question.getUser().getName(),
								question.getCreatedDate(),
								question.isCheck()
						)
				).collect(Collectors.toList());
	}

	public QuestionInformationResponse queryQuestionInformation(Long questionId) {
		Question question = questionRepository
				.findById(questionId)
				.orElseThrow(QuestionNotFoundException::new);

		return new QuestionInformationResponse(question.getDescription());
	}

	@Transactional
	public void questionResult(QuestionResultRequest request) {
		Question question = questionRepository
				.findById(request.getQuestionId())
				.orElseThrow(QuestionNotFoundException::new);

		if(question.isCheck())
			throw new AlreadyResultQuestionException();

		questionResultRepository.save(
				QuestionResult.builder()
				.question(question)
				.reason(request.getReason())
				.build()
		);

		question.check();
	}

}
