package io.github.tn1.server.entity.tag_notification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagNotificationRepository extends CrudRepository<TagNotification, Long> {
    List<TagNotification> findByTag(String tag);
}
