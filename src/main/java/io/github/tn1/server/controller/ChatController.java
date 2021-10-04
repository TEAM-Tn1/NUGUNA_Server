package io.github.tn1.server.controller;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.chat.request.JoinRequest;
import io.github.tn1.server.dto.chat.request.LeaveRequest;
import io.github.tn1.server.dto.chat.request.QueryMessageRequest;
import io.github.tn1.server.dto.chat.response.CarrotRoomResponse;
import io.github.tn1.server.dto.chat.response.GroupRoomResponse;
import io.github.tn1.server.dto.chat.response.JoinResponse;
import io.github.tn1.server.dto.chat.response.QueryMessageResponse;
import io.github.tn1.server.service.chat.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@DeleteMapping("/room")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void leaveRoom(@RequestBody @Valid LeaveRequest request) {
		chatService.leaveRoom(request.getRoomId());
	}

	@GetMapping("/carrot")
	public List<CarrotRoomResponse> queryCarrotRoom() {
		return chatService.queryCarrotRoom();
	}

	@GetMapping("/group")
	public List<GroupRoomResponse> queryGroupRoom() {
		return chatService.queryGroupRoom();
	}

	@GetMapping("/content")
	public List<QueryMessageResponse> queryMessage(@RequestBody QueryMessageRequest request, @RequestParam("page") int page) {
		return chatService.queryMessage(request, page);
	}

}
