package io.github.tn1.server.domain.report.service;

import io.github.tn1.server.domain.report.presentation.dto.request.FeedReportRequest;
import io.github.tn1.server.domain.report.presentation.dto.request.ReasonRequest;
import io.github.tn1.server.domain.report.presentation.dto.request.UserReportRequest;
import io.github.tn1.server.domain.report.presentation.dto.response.ReasonResponse;
import io.github.tn1.server.domain.report.presentation.dto.response.ReportResponse;
import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.domain.repository.FeedRepository;
import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.report.domain.repository.ReportRepository;
import io.github.tn1.server.domain.report.domain.types.ReportType;
import io.github.tn1.server.domain.report.domain.FeedReport;
import io.github.tn1.server.domain.report.domain.repository.FeedReportRepository;
import io.github.tn1.server.domain.report.domain.ReportMedium;
import io.github.tn1.server.domain.report.domain.repository.ReportMediumRepository;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.domain.repository.UserRepository;
import io.github.tn1.server.domain.report.exception.AlreadyReportedUserException;
import io.github.tn1.server.domain.feed.exception.FeedNotFoundException;
import io.github.tn1.server.domain.report.exception.NotYourReportException;
import io.github.tn1.server.domain.report.exception.ReportNotFoundException;
import io.github.tn1.server.domain.report.exception.ReportResultNotFoundException;
import io.github.tn1.server.domain.report.exception.SelfReportException;
import io.github.tn1.server.global.exception.TooManyFilesException;
import io.github.tn1.server.domain.user.exception.UserNotFoundException;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReportService {

	private static final ReportType FEED_REPORT = ReportType.F;
	private static final ReportType USER_REPORT = ReportType.U;

	private final UserFacade userFacade;
	private final S3Util s3Util;
	private final UserRepository userRepository;
	private final ReportRepository reportRepository;
	private final FeedRepository feedRepository;
	private final FeedReportRepository feedReportRepository;
	private final ReportMediumRepository reportMediumRepository;

	public ReportResponse userReport(UserReportRequest request) {
		if(request.getEmail().equals(userFacade.getCurrentEmail()))
			throw new SelfReportException();

		User currentUser = userFacade.getCurrentUser();

		User user = userRepository
				.findById(request.getEmail())
				.orElseThrow(UserNotFoundException::new);

		try {
			Report report = reportRepository.save(
					Report.builder()
							.title(request.getTitle())
							.contents(request.getContent())
							.defendant(user)
							.reporter(currentUser)
							.reportType(USER_REPORT)
							.build()
			);
			return new ReportResponse(
					report.getId()
			);
		} catch (RuntimeException e) {
			throw new AlreadyReportedUserException();
		}
	}

	public ReportResponse feedReport(FeedReportRequest request) {
		Feed feed = feedRepository
				.findById(request.getFeedId())
				.orElseThrow(FeedNotFoundException::new);

		if(feed.isWriter(userFacade.getCurrentEmail()))
			throw new SelfReportException();

		User currentUser = userFacade.getCurrentUser();
		User user = feed.getUser();

		Report report;

		try {
			report = reportRepository.save(
					Report.builder()
							.title(request.getTitle())
							.contents(request.getContent())
							.defendant(user)
							.reporter(currentUser)
							.reportType(FEED_REPORT)
							.build()
			);
		} catch (RuntimeException e) {
			throw new AlreadyReportedUserException();
		}

		feedReportRepository.save(
				FeedReport.builder()
						.report(report)
						.feed(feed)
						.build()
		);

		return new ReportResponse(report.getId());
	}

	public void postMedium(MultipartFile file, Long reportId) {
		Report report = reportRepository.findById(reportId)
				.orElseThrow(ReportNotFoundException::new);

		if(report.isNotReporter(userFacade.getCurrentEmail()))
			throw new NotYourReportException();

		if(reportMediumRepository.existsByReport(report))
			throw new TooManyFilesException();

		String url = s3Util.upload(file);

		reportMediumRepository.save(
				ReportMedium.builder()
						.report(report)
						.path(url)
						.build()
		);
	}

	public ReasonResponse queryReport(ReasonRequest request) {
		Report report = reportRepository
				.findById(request.getReportId())
				.orElseThrow(ReportNotFoundException::new);

		if(report.isNotReporter(userFacade.getCurrentEmail()))
			throw new NotYourReportException();

		if(report.getResult() == null)
			throw new ReportResultNotFoundException();

		return new ReasonResponse(report.getResult().getReason());
	}

}
