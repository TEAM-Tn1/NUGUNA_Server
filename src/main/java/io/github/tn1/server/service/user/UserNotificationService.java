package io.github.tn1.server.service.user;

import javax.transaction.Transactional;

import io.github.tn1.server.dto.user.response.NotificationResponse;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.facade.user.UserFacade;
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
