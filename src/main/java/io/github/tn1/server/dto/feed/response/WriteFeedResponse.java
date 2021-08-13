package io.github.tn1.server.dto.feed.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WriteFeedResponse {
    private Long feedId;
    private String title;
    private String description;
    private Integer price;
    private List<String> tags;
    private String photo;
    private Integer headCount;
    private LocalDate date;
    private boolean like;
    private Integer count;
    private boolean isUsedItem;

    public void setGroupFeed(Integer headCount, LocalDate date) {
        this.headCount = headCount;
        this.date = date;
    }

}