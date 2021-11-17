package io.github.tn1.server.domain.feed.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarrotResponse {

	@JsonUnwrapped
	private final DefaultFeedResponse defaultFeedResponse;

	private final String medium;

}
