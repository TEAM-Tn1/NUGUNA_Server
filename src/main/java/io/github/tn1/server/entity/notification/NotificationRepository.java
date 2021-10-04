package io.github.tn1.server.entity.notification;

import io.github.tn1.server.entity.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	int countByUserAndIsWatch(User user, boolean watch);
	Page<Notification> findByUser(User user, Pageable pageable);
}
