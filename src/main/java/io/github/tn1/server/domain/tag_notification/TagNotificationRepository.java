package io.github.tn1.server.domain.tag_notification;

import java.util.List;

import io.github.tn1.server.domain.user.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagNotificationRepository extends CrudRepository<TagNotification, Long> {
    List<TagNotification> findByTag(String tag);
    List<TagNotification> findByUser(User user);
}
