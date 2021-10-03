package io.github.tn1.server.service.notification;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.notification.response.TagResponse;
import io.github.tn1.server.entity.tag_notification.TagNotification;
import io.github.tn1.server.entity.tag_notification.TagNotificationRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.AlreadyRegisteredTagException;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.NotYourNotificationTagException;
import io.github.tn1.server.exception.NotificationTagNotFoundException;
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
		User user = userRepository
				.findById(userFacade.getEmail())
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

	public List<TagResponse> queryNotificationTag() {
		User user = userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);
		return tagNotificationRepository.findByUser(user)
				.stream()
				.map(tag -> new TagResponse(tag.getId(), tag.getTag()))
				.collect(Collectors.toList());
	}

	public void removeNotificationTag(long tagId) {
		TagNotification tag;
		tag = tagNotificationRepository.findById(tagId)
				.orElseThrow(NotificationTagNotFoundException::new);
		if(!tag.getUser().getEmail()
				.equals(userFacade.getEmail()))
			throw new NotYourNotificationTagException();
		tagNotificationRepository.delete(tag);
	}


}
