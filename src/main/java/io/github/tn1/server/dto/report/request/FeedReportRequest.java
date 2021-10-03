package io.github.tn1.server.dto.report.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedReportRequest {

	@NotNull(message = "title은 null이면 안됩니다.")
	private String title;

	@NotNull(message = "content은 null이면 안됩니다.")
	private String content;

	@NotNull(message = "feed_id은 null이면 안됩니다.")
	private Long feedId;

}
