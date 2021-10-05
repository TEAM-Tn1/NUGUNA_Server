package io.github.tn1.server.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.dto.admin.response.QuestionResponse;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.question.Question;
import io.github.tn1.server.entity.question.QuestionRepository;
import io.github.tn1.server.entity.report.ReportRepository;
import io.github.tn1.server.entity.report.result.ResultRepository;
import io.github.tn1.server.exception.QuestionNotFoundException;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionAdminService {

	private final FeedRepository feedRepository;
	private final ReportRepository reportRepository;
	private final ResultRepository resultRepository;
	private final QuestionRepository questionRepository;
	private final S3Util s3Util;

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

}
