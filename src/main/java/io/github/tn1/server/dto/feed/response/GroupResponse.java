package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupResponse {

	@JsonUnwrapped
	private final DefaultFeedResponse defaultFeedResponse;

	private final String medium;
	private final int headCount;
	private final int currentHeadCount;
	private final LocalDate date;

}
