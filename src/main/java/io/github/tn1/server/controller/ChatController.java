package io.github.tn1.server.controller;

import io.github.tn1.server.dto.chat.JoinRequest;
import io.github.tn1.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@PostMapping("/room")
	public String joinRoom(@RequestBody JoinRequest request) {
		return chatService.joinRoom(request.getFeedId());
	}

}
