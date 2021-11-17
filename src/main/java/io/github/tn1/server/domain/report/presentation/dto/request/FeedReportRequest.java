package io.github.tn1.server.domain.report.presentation.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedReportRequest {

	@NotNull(message = "title은 null이면 안됩니다.")
	@Size(max = 15, message = "title은 15자를 넘어서는 안됩니다.")
	private String title;

	@NotNull(message = "content은 null이면 안됩니다.")
	@Size(max = 1000, message = "content는 1000자를 넘어서는 안됩니다.")
	private String content;

	@NotNull(message = "feed_id은 null이면 안됩니다.")
	private Long feedId;

}
