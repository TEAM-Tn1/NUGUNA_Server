package io.github.tn1.server.domain.notification.presentation.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveTagRequest {

	@NotNull(message = "tag_id는 null이면 안됩니다.")
	private Long tagId;

}
