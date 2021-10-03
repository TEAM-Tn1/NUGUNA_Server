package io.github.tn1.server.controller;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.notification.request.RemoveTagRequest;
import io.github.tn1.server.dto.notification.request.TagRequest;
import io.github.tn1.server.dto.notification.response.CountResponse;
import io.github.tn1.server.dto.notification.response.NotificationResponse;
import io.github.tn1.server.dto.notification.response.TagResponse;
import io.github.tn1.server.service.notification.NotificationService;
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
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/tags")
	@ResponseStatus(HttpStatus.CREATED)
	public void setNotificationTag(@RequestBody @Valid TagRequest request) {
		notificationService
				.setNotificationTag(request.getTag());
	}

	@GetMapping("/tags")
	public List<TagResponse> queryNotificationTag() {
		return notificationService
				.queryNotificationTag();
	}

	@DeleteMapping("/tags")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeNotificationTag(@RequestBody @Valid RemoveTagRequest request) {
		notificationService
				.removeNotificationTag(request.getTagId());
	}

	@GetMapping("/count")
	public CountResponse countOfNotification() {
		return notificationService
				.countOfNotification();
	}

	@GetMapping("/list")
	public List<NotificationResponse> queryNotificationList(@RequestParam("page") int page) {
		return notificationService
				.queryNotificationList(page);
	}

}
