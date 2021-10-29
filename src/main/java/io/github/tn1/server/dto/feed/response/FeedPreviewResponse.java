package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FeedPreviewResponse {

	private final long feedId;
	private final String title;
	private final int price;
	private final List<String> tags;
	private final String medium;
	private boolean like;
	private final int count;

	private int headCount;
	private int currentHeadCount;
	private LocalDate date;

	public void setLike(boolean like) {
		this.like = like;
	}

	public void setGroupInformation(int headCount, int currentHeadCount,
			LocalDate date) {
		this.headCount = headCount;
		this.currentHeadCount = currentHeadCount;
		this.date = date;
	}

}
