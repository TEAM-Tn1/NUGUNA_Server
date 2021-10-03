package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedResponse {

	private final Long feedId;
	private final String title;
	private final String description;
	private final Integer price;
	private final List<String> tags;
	private final String medium;
	private final LocalDateTime lastModifyDate;
	private boolean like;
	private final Integer count;
    private Integer headCount;
    private Integer currentHeadCount;
    private LocalDate date;
    private final boolean isUsedItem;
	private final UserInfo userInfo;

	@Builder
	public FeedResponse(Long feedId, String title,
			String description, Integer price,
			List<String> tags, String medium,
			LocalDateTime lastModifyDate,
			boolean like, Integer count,
			Integer headCount, Integer currentHeadCount,
			LocalDate date, boolean isUsedItem,
			String writerEmail, String writerName) {
		this.feedId = feedId;
		this.title = title;
		this.description = description;
		this.price = price;
		this.tags = tags;
		this.medium = medium;
		this.lastModifyDate = lastModifyDate;
		this.like = like;
		this.count = count;
		this.headCount = headCount;
		this.currentHeadCount = currentHeadCount;
		this.date = date;
		this.isUsedItem = isUsedItem;
		this.userInfo =
				new UserInfo(writerEmail, writerName);
	}

    public void setGroupFeed(Integer headCount, Integer currentHeadCount, LocalDate date) {
        this.headCount = headCount;
        this.currentHeadCount = currentHeadCount;
        this.date = date;
    }

    public void setLike(boolean like) {
		this.like = like;
	}

	@Getter
    @AllArgsConstructor
    private static class UserInfo{
		private final String writerEmail;
		private final String writerName;
	}

}
