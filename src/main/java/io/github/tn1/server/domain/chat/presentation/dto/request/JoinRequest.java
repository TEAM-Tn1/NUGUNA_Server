package io.github.tn1.server.domain.chat.presentation.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

	@NotNull(message = "feed_id는 null이면 안됩니다.")
	private Long feedId;

}
