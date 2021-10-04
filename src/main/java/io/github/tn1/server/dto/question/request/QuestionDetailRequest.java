package io.github.tn1.server.dto.question.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionDetailRequest {

	@NotNull(message = "question_id는 null이면 안됩니다.")
	private Long questionId;

}
