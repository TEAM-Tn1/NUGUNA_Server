package io.github.tn1.server.domain.user.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.user.presentation.dto.admin.request.QuestionResultRequest;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.QuestionResponse;
import io.github.tn1.server.domain.question.domain.Question;
import io.github.tn1.server.domain.question.domain.repository.QuestionRepository;
import io.github.tn1.server.domain.question.domain.QuestionResult;
import io.github.tn1.server.domain.question.domain.repository.QuestionResultRepository;
import io.github.tn1.server.domain.question.exception.AlreadyResultQuestionException;
import io.github.tn1.server.domain.question.exception.QuestionNotFoundException;
import io.github.tn1.server.global.utils.fcm.FcmUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionAdminService {

	private final QuestionRepository questionRepository;
	private final QuestionResultRepository questionResultRepository;
	private final FcmUtil fcmUtil;

	public List<QuestionResponse> queryQuestion(Pageable pageable) {
		return questionRepository.findAll(pageable)
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

		fcmUtil.sendQuestionResultNotification(question);
	}

}
