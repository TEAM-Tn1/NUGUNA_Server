package io.github.tn1.server.controller;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.chat.request.JoinRequest;
import io.github.tn1.server.dto.chat.response.CarrotRoomResponse;
import io.github.tn1.server.dto.chat.response.JoinResponse;
import io.github.tn1.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@PostMapping("/room")
	public JoinResponse joinRoom(@RequestBody @Valid JoinRequest request) {
		return chatService.joinRoom(request.getFeedId());
	}

	@GetMapping("/carrot")
	public List<CarrotRoomResponse> getCarrotRoom() {
		return chatService.queryCarrotRoom();
	}

}
