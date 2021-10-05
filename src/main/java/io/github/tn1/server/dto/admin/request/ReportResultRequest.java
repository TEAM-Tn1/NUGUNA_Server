package io.github.tn1.server.dto.admin.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportResultRequest {

	@NotNull(message = "report_id는 null이면 안됩니다.")
	private Long reportId;

	@NotNull(message = "remove는 null이면 안됩니다.")
	private Boolean remove;

	@NotNull(message = "reason은 null이면 안됩니다.")
	@Size(max = 100, message = "reason은 100자를 넘어서는 안됩니다.")
	private String reason;


}
