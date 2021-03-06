package io.github.tn1.server.domain.feed.presentation.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultFeedResponse {

	private final long feedId;
	private final String title;
	private final int price;
	private final List<String> tags;
	private boolean like;
	private final int count;

	public void setLike(boolean like) {
		this.like = like;
	}

}
