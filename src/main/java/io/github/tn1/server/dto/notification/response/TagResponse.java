package io.github.tn1.server.dto.notification.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagResponse {

	private final Long tagId;
	private final String tag;

}
