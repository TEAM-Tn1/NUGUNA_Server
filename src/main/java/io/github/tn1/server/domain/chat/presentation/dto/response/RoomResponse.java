package io.github.tn1.server.domain.chat.presentation.dto.response;

import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.types.RoomType;
import lombok.Getter;

@Getter
public class RoomResponse {

	private final String roomId;
	private final String roomName;
	private final String lastMessage;
	private final String photoUrl;

	public RoomResponse(Room room, Message message, String currentEmail) {
		this.roomId = room.getId();
		this.photoUrl = room.getPhotoUrl();
		if(room.getType().equals(RoomType.CARROT)) {
			this.roomName = room.otherMember(currentEmail) != null ?
					room.otherMember(currentEmail).getName() : null;
		} else {
			this.roomName = room.getTitle();
		}
		lastMessage = message != null ? message.getContent() : null;
	}

}
