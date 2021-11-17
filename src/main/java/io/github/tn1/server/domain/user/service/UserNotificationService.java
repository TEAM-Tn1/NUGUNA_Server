package io.github.tn1.server.domain.user.service;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.user.presentation.dto.user.response.NotificationResponse;
import io.github.tn1.server.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

	private final UserFacade userFacade;

	public NotificationResponse queryNotification() {
		return new NotificationResponse(
				userFacade.getCurrentUser()
				.isNotification());
	}

	@Transactional
	public void ableNotification() {
		userFacade.getCurrentUser()
				.ableNotification();
	}

	@Transactional
	public void disableNotification() {
		userFacade.getCurrentUser()
				.disableNotification();
	}

}
