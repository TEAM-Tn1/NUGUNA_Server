package io.github.tn1.server.domain.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.notification.presentation.dto.response.CountResponse;
import io.github.tn1.server.domain.notification.presentation.dto.response.NotificationResponse;
import io.github.tn1.server.domain.notification.presentation.dto.response.TagResponse;
import io.github.tn1.server.domain.notification.domain.NotificationEntity;
import io.github.tn1.server.domain.notification.domain.repository.NotificationRepository;
import io.github.tn1.server.domain.tag_notification.TagNotification;
import io.github.tn1.server.domain.tag_notification.TagNotificationRepository;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.notification.exception.AlreadyRegisteredTagException;
import io.github.tn1.server.domain.notification.exception.NotYourNotificationException;
import io.github.tn1.server.domain.notification.exception.NotYourNotificationTagException;
import io.github.tn1.server.domain.notification.exception.NotificationNotFoundException;
import io.github.tn1.server.domain.notification.exception.NotificationTagNotFoundException;
import io.github.tn1.server.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final UserFacade userFacade;
	private final TagNotificationRepository tagNotificationRepository;
	private final NotificationRepository notificationRepository;

	public void setNotificationTag(String tag) {
		User user = userFacade.getCurrentUser();

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
		User user = userFacade.getCurrentUser();

		return tagNotificationRepository.findByUser(user)
				.stream()
				.map(tag -> new TagResponse(tag.getId(), tag.getTag()))
				.collect(Collectors.toList());
	}

	public void removeNotificationTag(long tagId) {
		TagNotification tag;
		tag = tagNotificationRepository.findById(tagId)
				.orElseThrow(NotificationTagNotFoundException::new);

		if(!tag.isOwner(userFacade.getCurrentEmail()))
			throw new NotYourNotificationTagException();

		tagNotificationRepository.delete(tag);
	}

	public CountResponse countOfNotification() {
		User user = userFacade.getCurrentUser();

		return new CountResponse(
				notificationRepository
				.countByUserAndIsWatch(user, false));
	}

	public List<NotificationResponse> queryNotificationList(int page) {
		User user = userFacade.getCurrentUser();

		return notificationRepository
				.findByUser(user, PageRequest.of(page, 5))
				.stream().map(notification ->
				new NotificationResponse(
						notification.getId(), notification.getTitle(),
						notification.getMessage(), notification.getContent(),
						notification.isWatch())
		).collect(Collectors.toList());
	}

	@Transactional
	public void checkNotification(Long notificationId) {
		NotificationEntity notification = notificationRepository
				.findById(notificationId)
				.orElseThrow(NotificationNotFoundException::new);

		if(!notification.isOwner(userFacade.getCurrentEmail()))
			throw new NotYourNotificationException();

		notification.watch();
	}

}
