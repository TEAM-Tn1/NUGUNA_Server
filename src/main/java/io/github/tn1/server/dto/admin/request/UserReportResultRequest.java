package io.github.tn1.server.dto.admin.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReportResultRequest {

	@NotNull(message = "report_id는 null이면 안됩니다.")
	private Long reportId;

	private LocalDate blackDate;

	@NotNull(message = "reason은 null이면 안됩니다.")
	@Size(max = 250, message = "reason은 250자를 넘어서는 안됩니다.")
	private String reason;

}
