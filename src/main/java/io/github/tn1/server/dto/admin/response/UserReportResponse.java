package io.github.tn1.server.dto.admin.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReportResponse {

	private final long reportId;
	private final String title;
	private final String reporterName;
	private final String defendantName;
	private final LocalDateTime createdDate;
	private final boolean check;

}
