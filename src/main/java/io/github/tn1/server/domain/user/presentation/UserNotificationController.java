package io.github.tn1.server.domain.user.presentation;

import io.github.tn1.server.domain.user.presentation.dto.user.response.NotificationResponse;
import io.github.tn1.server.domain.user.service.UserNotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ableNotification() {
		userNotificationService
				.ableNotification();
  }
  
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableNotification() {
		userNotificationService
				.disableNotification();
	}

}
