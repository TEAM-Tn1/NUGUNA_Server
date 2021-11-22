package io.github.tn1.server.domain.chat.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tn1.server.domain.chat.domain.types.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageDto {

	@JsonProperty("room_id")
	private final String roomId;

	@JsonProperty("message_id")
	private final Long messageId;

	private final String content;

	@JsonProperty("sent_at")
	private final String sentAt;

	private final MessageType type;

	private final String name;

	private final String email;

}
