package io.github.tn1.server.dto.notification.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

	private final Long notificationId;
	private final String title;
	private final String message;
	private final String content;
	private final boolean isWatch;

}
