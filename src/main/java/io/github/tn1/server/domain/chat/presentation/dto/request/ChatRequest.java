package io.github.tn1.server.domain.chat.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRequest {

	private String message;

	@JsonProperty("room_id")
	private String roomId;

}
