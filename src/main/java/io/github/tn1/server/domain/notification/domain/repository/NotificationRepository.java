package io.github.tn1.server.domain.notification.domain.repository;

import io.github.tn1.server.domain.notification.domain.NotificationEntity;
import io.github.tn1.server.domain.user.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
	int countByUserAndIsWatch(User user, boolean watch);
	Page<NotificationEntity> findByUser(User user, Pageable pageable);
}
