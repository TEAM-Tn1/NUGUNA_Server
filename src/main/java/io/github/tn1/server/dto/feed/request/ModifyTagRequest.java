package io.github.tn1.server.dto.feed.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyTagRequest {

	@NotNull(message = "feed_id는 null이면 안됩니다.")
	private Long feedId;

	private String[] tags;

}
