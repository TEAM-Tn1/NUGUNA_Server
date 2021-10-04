package io.github.tn1.server.service.report;

import io.github.tn1.server.dto.report.request.FeedReportRequest;
import io.github.tn1.server.dto.report.request.ReasonRequest;
import io.github.tn1.server.dto.report.request.UserReportRequest;
import io.github.tn1.server.dto.report.response.ReasonResponse;
import io.github.tn1.server.dto.report.response.ReportResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.report.Report;
import io.github.tn1.server.entity.report.ReportRepository;
import io.github.tn1.server.entity.report.ReportType;
import io.github.tn1.server.entity.report.feed_report.FeedReport;
import io.github.tn1.server.entity.report.feed_report.FeedReportRepository;
import io.github.tn1.server.entity.report.medium.ReportMedium;
import io.github.tn1.server.entity.report.medium.ReportMediumRepository;
import io.github.tn1.server.entity.report.result.ResultRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.AlreadyReportedUserException;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.exception.NotYourReportException;
import io.github.tn1.server.exception.ReportNotFoundException;
import io.github.tn1.server.exception.ReportResultNotFoundException;
import io.github.tn1.server.exception.SelfReportException;
import io.github.tn1.server.exception.TooManyFilesException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final UserFacade userFacade;
	private final S3Util s3Util;
	private final UserRepository userRepository;
	private final ReportRepository reportRepository;
	private final FeedRepository feedRepository;
	private final FeedReportRepository feedReportRepository;
	private final ReportMediumRepository reportMediumRepository;
	private final ResultRepository resultRepository;

	public ReportResponse userReport(UserReportRequest request) {
		if(request.getEmail().equals(userFacade.getEmail()))
			throw new SelfReportException();

		User currentUser = userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);
		User user = userRepository
				.findById(request.getEmail())
				.orElseThrow(UserNotFoundException::new);

		try {
			return new ReportResponse(
					reportRepository.save(
							Report.builder()
									.title(request.getTitle())
									.contents(request.getContent())
									.defendant(user)
									.reporter(currentUser)
									.reportType(ReportType.U)
									.build()
					).getId()
			);
		} catch (RuntimeException e) {
			throw new AlreadyReportedUserException();
		}
	}

	public ReportResponse feedReport(FeedReportRequest request) {
		Feed feed = feedRepository
				.findById(request.getFeedId())
				.orElseThrow(FeedNotFoundException::new);

		if(feed.getUser().matchEmail(userFacade.getEmail()))
			throw new SelfReportException();

		User currentUser = userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);
		User user = feed.getUser();


		Report report;

		try {
			report = reportRepository.save(
					Report.builder()
							.title(request.getTitle())
							.contents(request.getContent())
							.defendant(user)
							.reporter(currentUser)
							.reportType(ReportType.F)
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

		if(!report.getReporter().matchEmail(userFacade.getEmail()))
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

		if(!report.getReporter()
				.matchEmail(userFacade.getEmail()))
			throw new NotYourReportException();

		if(report.getResult() == null)
			throw new ReportResultNotFoundException();

		return new ReasonResponse(report.getResult().getReason());
	}

}
