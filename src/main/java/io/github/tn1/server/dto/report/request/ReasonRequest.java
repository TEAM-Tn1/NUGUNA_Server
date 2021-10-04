package io.github.tn1.server.dto.report.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReasonRequest {

	@NotNull(message = "report_id는 null이면 안됩니다.")
	private Long reportId;

}
