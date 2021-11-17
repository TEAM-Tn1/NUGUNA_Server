package io.github.tn1.server.domain.question.presentation.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequest {

	@NotNull(message = "title은 null이면 안됩니다.")
	@Size(max = 30, message = "title는 30자를 넘어서는 안됩니다.")
	private String title;

	@NotNull(message = "title은 null이면 안됩니다.")
	@Size(max = 1000, message = "description은 1000자를 넘어서는 안됩니다.")
	private String description;

}
