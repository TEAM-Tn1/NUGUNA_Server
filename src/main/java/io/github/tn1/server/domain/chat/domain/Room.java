package io.github.tn1.server.domain.chat.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import io.github.tn1.server.domain.chat.domain.types.RoomType;
import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.user.domain.User;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	@Enumerated(EnumType.STRING)
	@Column(length = 6)
	private RoomType type;

	private String photoUrl;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.REMOVE)
	private final Set<Member> member = new HashSet<>();

	@Builder
	public Room(User user, Feed feed, RoomType type, String photoUrl) {
		this.headUser = user;
		this.feed = feed;
		this.type = type;
		this.photoUrl = photoUrl;
	}

	public void initPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public boolean isGroupRoom() {
		return type.equals(RoomType.GROUP);
	}

	public Member otherMember(String email) {
		List<Member> memberList = member.stream()
				.filter(m -> !m.getUser().getEmail().equals(email))
				.collect(Collectors.toList());
		if(memberList.size() >= 1)
			return memberList.get(0);
		return null;
	}

	public String getTitle() {
		return feed.getTitle();
	}

	public int getCurrentCount() {
		return feed.getCurrentCount();
	}

}
