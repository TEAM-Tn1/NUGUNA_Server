package io.github.tn1.server.dto.notification.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagRequest {

	@Size(max = 10, message = "tag는 10자를 넘어서는 안됩니다.")
	@NotEmpty(message = "tag는 비어있으면 안됩니다.")
	private String tag;

}
