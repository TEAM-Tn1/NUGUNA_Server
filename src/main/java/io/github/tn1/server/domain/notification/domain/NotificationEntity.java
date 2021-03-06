package io.github.tn1.server.domain.notification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.github.tn1.server.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_notification")
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 45)
	private String title;

	private String message;

	@Column(length = 45)
	private String content;

	@Column(columnDefinition = "BIT(1) default false")
	private boolean isWatch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private User user;

	@Builder
	public NotificationEntity(String title, String message,
			String content, User user) {
		this.title = title;
		this.message = message;
		this.content = content;
		this.user = user;
	}

	public void watch() {
		this.isWatch = true;
	}

	public boolean isOwner(String email) {
		return user.getEmail().equals(email);
	}

}
