package io.github.tn1.server.dto.feed.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyTagRequest {

	@NotNull(message = "feed_id는 null이면 안됩니다.")
	private Long feedId;

	private List<@Size(max = 10, message = "tag는 10자를 넘어서는 안됩니다.") String> tags;

}
