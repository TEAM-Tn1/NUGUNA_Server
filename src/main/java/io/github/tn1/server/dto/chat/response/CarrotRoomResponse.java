package io.github.tn1.server.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarrotRoomResponse {

	private final String roomId;
	private final String roomName;
	private final String lastMessage;
	private final String photoUrl;

}
