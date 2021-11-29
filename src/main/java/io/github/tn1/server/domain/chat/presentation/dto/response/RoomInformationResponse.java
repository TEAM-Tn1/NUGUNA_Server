package io.github.tn1.server.domain.chat.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomInformationResponse {

	private final String roomName;
	private final int count;

}
