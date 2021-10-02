package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.response.NotificationResponse;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

	private UserFacade userFacade;
	private UserRepository userRepository;

	public NotificationResponse queryNotification() {
		boolean notification;
		notification = userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new)
				.isNotification();
		return new NotificationResponse(notification);
	}

}
