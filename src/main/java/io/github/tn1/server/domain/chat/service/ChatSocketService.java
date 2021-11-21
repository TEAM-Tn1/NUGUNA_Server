package io.github.tn1.server.domain.chat.service;

import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.presentation.dto.MessageDto;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.socket.Name;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatSocketService {

	private final UserFacade userFacade;

	public void sendChatMessage(Message message, User user, String roomId, SocketIOServer server) {
		MessageDto messageDto = MessageDto.builder()
				.roomId(roomId)
				.content(message.getContent())
				.email(user.getEmail())
				.name(user.getName())
				.sentAt(message.getSentAt().toString())
				.type(message.getType())
				.build();
		server.getRoomOperations(roomId)
				.getClients()
				.forEach(client ->
					client.sendEvent(Name.Message.name(), messageDto)
				);
	}

}
