package io.github.tn1.server.service.admin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.dto.admin.request.FeedReportResultRequest;
import io.github.tn1.server.dto.admin.request.UserReportResultRequest;
import io.github.tn1.server.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.dto.admin.response.ReportInformationResponse;
import io.github.tn1.server.dto.admin.response.UserReportResponse;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.question.QuestionRepository;
import io.github.tn1.server.entity.report.Report;
import io.github.tn1.server.entity.report.ReportRepository;
import io.github.tn1.server.entity.report.ReportType;
import io.github.tn1.server.entity.report.result.Result;
import io.github.tn1.server.entity.report.result.ResultRepository;
import io.github.tn1.server.exception.AlreadyResultReportException;
import io.github.tn1.server.exception.DateIsBeforeException;
import io.github.tn1.server.exception.NotFeedReportException;
import io.github.tn1.server.exception.ReportNotFoundException;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportAdminService {

	private final FeedRepository feedRepository;
	private final ReportRepository reportRepository;
	private final ResultRepository resultRepository;
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

	@Transactional
	public void feedReportResult(FeedReportResultRequest request) {
		Report report = reportRepository
				.findById(request.getReportId())
				.orElseThrow(ReportNotFoundException::new);

		if(report.isNotFeedReport())
			throw new NotFeedReportException();

		if(report.isCheck())
			throw new AlreadyResultReportException();

		if(request.getRemove())
			feedRepository.delete(report.getFeedReport().getFeed());

		report.check();

		resultRepository.save(
				Result.builder()
						.reason(request.getReason())
						.report(report)
						.build()
		);
	}

	public void userReportResult(UserReportResultRequest request) {
		Report report = reportRepository
				.findById(request.getReportId())
				.orElseThrow(ReportNotFoundException::new);

		if(report.isNotUserReport())
			throw new NotFeedReportException();

		if(report.isCheck())
			throw new AlreadyResultReportException();

		if(request.getBlackDate() != null) {
			if(request.getBlackDate().before(new Date()))
				throw new DateIsBeforeException();
			else report.getDefendant().changeBlackDate(
					LocalDate.ofInstant(
							request.getBlackDate().toInstant(),
							ZoneId.of("Asia/Seoul")));
		}

		report.check();

		resultRepository.save(
				Result.builder()
						.reason(request.getReason())
						.report(report)
						.build()
		);
	}

}
