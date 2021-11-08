package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedResponse {

	@JsonUnwrapped
	private final DefaultFeedResponse defaultFeedResponse;

	private final String description;
	private final LocalDateTime lastModifyDate;
	private final List<String> photo;
    private Integer headCount;
    private Integer currentHeadCount;
    private LocalDate date;
    private final boolean isUsedItem;
	private final UserInfo userInfo;

	@Builder
	public FeedResponse(DefaultFeedResponse defaultFeedResponse,
			String description, List<String> photo,
			LocalDateTime lastModifyDate,
			Integer headCount, Integer currentHeadCount,
			LocalDate date, boolean isUsedItem,
			String writerEmail, String writerName) {
		this.defaultFeedResponse = defaultFeedResponse;
		this.description = description;
		this.photo = photo;
		this.lastModifyDate = lastModifyDate;
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

	@Getter
    @AllArgsConstructor
    private static class UserInfo{
		private final String writerEmail;
		private final String writerName;
	}

}
