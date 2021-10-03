package io.github.tn1.server.service.notification;

import io.github.tn1.server.entity.tag_notification.TagNotification;
import io.github.tn1.server.entity.tag_notification.TagNotificationRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.AlreadyRegisteredTagException;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final UserFacade userFacade;
	private final UserRepository userRepository;
	private final TagNotificationRepository tagNotificationRepository;

	public void setNotificationTag(String tag) {
		User user = userRepository.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);
		try {
			tagNotificationRepository.save(
					TagNotification.builder()
							.user(user)
							.tag(tag)
							.build()
			);
		} catch (RuntimeException e) {
			throw new AlreadyRegisteredTagException();
		}
	}


}
