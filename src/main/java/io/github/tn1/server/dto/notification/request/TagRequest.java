package io.github.tn1.server.dto.notification.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagRequest {

	@NotEmpty
	private String tag;

}
