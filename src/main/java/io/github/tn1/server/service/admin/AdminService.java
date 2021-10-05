package io.github.tn1.server.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.dto.admin.response.QuestionInformationResponse;
import io.github.tn1.server.dto.admin.response.QuestionResponse;
import io.github.tn1.server.dto.admin.response.ReportInformationResponse;
import io.github.tn1.server.dto.admin.response.UserReportResponse;
import io.github.tn1.server.entity.question.Question;
import io.github.tn1.server.entity.question.QuestionRepository;
import io.github.tn1.server.entity.report.Report;
import io.github.tn1.server.entity.report.ReportRepository;
import io.github.tn1.server.entity.report.ReportType;
import io.github.tn1.server.exception.QuestionNotFoundException;
import io.github.tn1.server.exception.ReportNotFoundException;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final ReportRepository reportRepository;
	private final QuestionRepository questionRepository;
	private final S3Util s3Util;

	public List<FeedReportResponse> queryFeedReport() {
		return reportRepository.findByReportType(ReportType.F)
				.stream().map(report ->
					new FeedReportResponse(
							report.getId(),
							report.getTitle(),
							report.getReporter().getName(),
							report.getCreatedDate(),
							report.isCheck()
					)
		).collect(Collectors.toList());
	}

	public List<UserReportResponse> queryUserReport() {
		return reportRepository.findByReportType(ReportType.U)
				.stream().map(report ->
					new UserReportResponse(
							report.getId(),
							report.getTitle(),
							report.getReporter().getName(),
							report.getDefendant().getName(),
							report.getCreatedDate(),
							report.isCheck()
					)
				).collect(Collectors.toList());
	}

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

	public ReportInformationResponse queryReportInformation(Long reportId) {
		Report report = reportRepository
				.findById(reportId)
				.orElseThrow(ReportNotFoundException::new);

		return new ReportInformationResponse(
				report.getContents(),
				report.getReportMedium() == null ? null :
						s3Util.getObjectUrl(
								report.getReportMedium().getPath()
						)
		);
	}

	public QuestionInformationResponse queryQuestionInformation(Long questionId) {
		Question question = questionRepository
				.findById(questionId)
				.orElseThrow(QuestionNotFoundException::new);

		return new QuestionInformationResponse(question.getDescription());
	}

}
