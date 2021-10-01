package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedResponse extends FeedElementResponse {

    private Integer headCount;
    private Integer currentHeadCount;
    private LocalDate date;
    private final boolean isUsedItem;

    @Builder(builderMethodName = "WriteFeedResponseBuilder")
    public FeedResponse(Long feedId, String title, String description,
			Integer price, List<String> tags, String photo,
			LocalDateTime lastModifyDate, boolean like, Integer count,
			Integer headCount, LocalDate date, boolean isUsedItem) {
    	super(feedId, title, description, price,
				tags, photo, lastModifyDate, like, count);
    	this.headCount = headCount;
    	this.date = date;
    	this.isUsedItem = isUsedItem;
	}

    public void setGroupFeed(Integer headCount, Integer currentHeadCount, LocalDate date) {
        this.headCount = headCount;
        this.currentHeadCount = currentHeadCount;
        this.date = date;
    }

    public void setLike(boolean value) {
        super.setLike(value);
    }

}
