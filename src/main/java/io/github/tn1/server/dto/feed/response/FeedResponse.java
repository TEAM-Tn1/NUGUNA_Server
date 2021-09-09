package io.github.tn1.server.dto.feed.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedResponse {

    private Long feedId;
    private String title;
    private String description;
    private Integer price;
    private List<String> tags;
    private String photo;
    private LocalDateTime lastModifyDate;
    private boolean like;
    private Integer count;

    public void setLike(boolean value) {
        this.like = value;
    }

}
