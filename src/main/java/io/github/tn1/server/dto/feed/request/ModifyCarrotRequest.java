package io.github.tn1.server.dto.feed.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyCarrotRequest {
    private Long feedId;
    private String title;
    private String description;
    private Integer price;
}
