package io.github.tn1.server.dto.feed.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyCarrotRequest {

	@NotNull(message = "feed_id는 null이면 안됩니다.")
    private Long feedId;

	@NotBlank(message = "title은 비어있으면 안됩니다.")
    private String title;

    private String description;

	@NotNull(message = "price는 null이면 안됩니다.")
    private Integer price;
}
