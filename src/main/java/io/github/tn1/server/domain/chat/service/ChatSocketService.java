package io.github.tn1.server.domain.chat.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.presentation.dto.MessageDto;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.global.socket.SocketProperty;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatSocketService {

	public void sendChatMessage(Message message, User user, String roomId, SocketIOClient sendClient, SocketIOServer server) {
		MessageDto messageDto = MessageDto.builder()
				.roomId(roomId)
				.messageId(message.getId())
				.content(message.getContent())
				.email(user.getEmail())
				.name(user.getName())
				.sentAt(message.getSentAt().toString())
				.type(message.getType())
				.build();
		server.getRoomOperations(roomId)
				.getClients()
				.forEach(client ->
					client.sendEvent(SocketProperty.MESSAGE_KEY, messageDto)
				);
		sendClient.sendEvent(SocketProperty.MESSAGE_KEY, messageDto);
	}

}
