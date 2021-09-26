package io.github.tn1.server.entity.chat.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_room")
public class Room {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(36)")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "head_email")
	private User headUser;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	@Enumerated(EnumType.STRING)
	@Column(length = 6)
	private RoomType type;

	private String photoUrl;

	@Builder
	public Room(User user, Feed feed, RoomType type, String photoUrl) {
		this.headUser = user;
		this.feed = feed;
		this.type = type;
		this.photoUrl = photoUrl;
	}

}
