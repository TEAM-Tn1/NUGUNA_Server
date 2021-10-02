package io.github.tn1.server.controller.user;

import io.github.tn1.server.dto.user.response.NotificationResponse;
import io.github.tn1.server.service.user.UserNotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/notification")
@RequiredArgsConstructor
public class UserNotificationController {

	private final UserNotificationService userNotificationService;

	@GetMapping
	public NotificationResponse queryNotification() {
		return userNotificationService
				.queryNotification();
	}

}
