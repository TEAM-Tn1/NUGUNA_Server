package io.github.tn1.server.service.report;

import io.github.tn1.server.dto.report.request.UserReportRequest;
import io.github.tn1.server.dto.report.response.ReportResponse;
import io.github.tn1.server.entity.report.Report;
import io.github.tn1.server.entity.report.ReportRepository;
import io.github.tn1.server.entity.report.ReportType;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.AlreadyReportedUserException;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.SelfReportException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final UserFacade userFacade;
	private final UserRepository userRepository;
	private final ReportRepository reportRepository;

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

}
