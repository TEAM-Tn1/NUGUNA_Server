package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GroupResponse {

	@JsonUnwrapped
	private final DefaultFeedResponse defaultFeedResponse;

	private final int headCount;
	private final int currentHeadCount;
	private final LocalDate date;

}
