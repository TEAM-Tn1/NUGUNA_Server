package io.github.tn1.server.dto.feed.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WriteFeedResponse extends FeedResponse {

    private Integer headCount;
    private LocalDate date;
    private boolean isUsedItem;

    @Builder(builderMethodName = "WriteFeedResponseBuilder")
    public WriteFeedResponse(Long feedId, String title, String description,
			Integer price, List<String> tags, String photo,
			LocalDateTime lastModifyDate, boolean like, Integer count,
			Integer headCount, LocalDate date, boolean isUsedItem) {
    	super(feedId, title, description, price,
				tags, photo, lastModifyDate, like, count);
    	this.headCount = headCount;
    	this.date = date;
    	this.isUsedItem = isUsedItem;
	}

    public void setGroupFeed(Integer headCount, LocalDate date) {
        this.headCount = headCount;
        this.date = date;
    }

    public void setLike(boolean value) {
        super.setLike(value);
    }

}
