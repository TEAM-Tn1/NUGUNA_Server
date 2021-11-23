package io.github.tn1.server.domain.user.service.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.user.presentation.dto.admin.request.FeedReportResultRequest;
import io.github.tn1.server.domain.user.presentation.dto.admin.request.UpdateUserBlackDateRequest;
import io.github.tn1.server.domain.user.presentation.dto.admin.request.UserReportResultRequest;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.FeedReportResponse;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.ReportInformationResponse;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.UserBlackDateResponse;
import io.github.tn1.server.domain.user.presentation.dto.admin.response.UserReportResponse;
import io.github.tn1.server.domain.feed.domain.repository.FeedRepository;
import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.report.domain.repository.ReportRepository;
import io.github.tn1.server.domain.report.domain.types.ReportType;
import io.github.tn1.server.domain.report.domain.Result;
import io.github.tn1.server.domain.report.domain.repository.ResultRepository;
import io.github.tn1.server.domain.report.exception.AlreadyResultReportException;
import io.github.tn1.server.domain.feed.exception.DateIsBeforeException;
import io.github.tn1.server.domain.report.exception.NotFeedReportException;
import io.github.tn1.server.domain.report.exception.NotUserReportException;
import io.github.tn1.server.domain.report.exception.ReportNotFoundException;
import io.github.tn1.server.domain.report.exception.ReportResultNotFoundException;
import io.github.tn1.server.global.utils.fcm.FcmUtil;
import io.github.tn1.server.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportAdminService {

	private static final ReportType FEED_REPORT = ReportType.F;
	private static final ReportType USER_REPORT = ReportType.U;

	private final FeedRepository feedRepository;
	private final ReportRepository reportRepository;
	private final ResultRepository resultRepository;
	private final S3Util s3Util;
	private final FcmUtil fcmUtil;

	public List<FeedReportResponse> queryFeedReport(Pageable pageable) {
		return reportRepository.findByReportType(FEED_REPORT, pageable)
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

	public List<UserReportResponse> queryUserReport(Pageable pageable) {
		return reportRepository.findByReportType(USER_REPORT, pageable)
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
						report.getReportMedium().getPath()
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

		if(Boolean.TRUE.equals(request.getRemove()))
			feedRepository.delete(report.getFeedReport().getFeed());

		report.check();

		resultRepository.save(
				Result.builder()
						.reason(request.getReason())
						.report(report)
						.build()
		);

		fcmUtil.sendFeedResultNotification(report, request.getRemove());
	}

	public void userReportResult(UserReportResultRequest request) {
		Report report = reportRepository
				.findById(request.getReportId())
				.orElseThrow(ReportNotFoundException::new);

		if(report.isNotUserReport())
			throw new NotUserReportException();

		if(report.isCheck())
			throw new AlreadyResultReportException();

		if(request.getBlackDate() != null) {
			if(request.getBlackDate().isBefore(LocalDate.now()))
				throw new DateIsBeforeException();
			else report.getDefendant()
					.changeBlackDate(request.getBlackDate());
		}

		report.check();

		resultRepository.save(
				Result.builder()
						.reason(request.getReason())
						.report(report)
						.build()
		);

		fcmUtil.sendUserResultNotification(report, request.getBlackDate() != null);
	}

	public UserBlackDateResponse queryUserBlackDate(Long reportId) {
		Report report = queryUserReport(reportId);

		LocalDate blackDate = report.getDefendant().getBlackDate();

		return new UserBlackDateResponse(
				blackDate.isBefore(LocalDate.now()) ?
						null : blackDate
		);
	}

	public void updateUserBlackDate(Long reportId, UpdateUserBlackDateRequest request) {
		Report report = queryUserReport(reportId);

		report.getDefendant()
				.changeBlackDate(request.getBlackDate());
	}

	private Report queryUserReport(Long reportId) {
		Report report = reportRepository
				.findById(reportId)
				.orElseThrow(ReportNotFoundException::new);

		if(report.isNotUserReport())
			throw new NotUserReportException();

		if(!report.isCheck())
			throw new ReportResultNotFoundException();

		return report;
	}

}
