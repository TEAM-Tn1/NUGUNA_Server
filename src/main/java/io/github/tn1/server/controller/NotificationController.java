package io.github.tn1.server.controller;

import java.util.List;

import io.github.tn1.server.dto.notification.request.TagRequest;
import io.github.tn1.server.dto.notification.response.TagResponse;
import io.github.tn1.server.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void setNotificationTag(@RequestBody TagRequest request) {
		notificationService
				.setNotificationTag(request.getTag());
	}

	@GetMapping("/tags")
	public List<TagResponse> queryNotificationTag() {
		return notificationService
				.queryNotificationTag();
	}

}
