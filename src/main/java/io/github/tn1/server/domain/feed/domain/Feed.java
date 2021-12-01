package io.github.tn1.server.domain.feed.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import io.github.tn1.server.domain.chat.exception.RoomNotFoundException;
import io.github.tn1.server.global.entity.BaseTimeEntity;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.like.domain.Like;
import io.github.tn1.server.domain.report.domain.FeedReport;
import io.github.tn1.server.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_feed")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(length = 1000)
    private String description;

    private Integer price;

    @Column(columnDefinition = "BIT(1)")
    private boolean isUsedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;

    private int count;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private final Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private final Set<FeedMedium> media = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private final Set<Like> likes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private final Set<FeedReport> feedReports = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
	private Group group;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
	private final Set<Room> room = new HashSet<>();

    @Builder
    public Feed(String title, String description, Integer price,
                boolean isUsedItem, User user) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.isUsedItem = isUsedItem;
        this.user = user;
    }

    public Feed changeTitle(String title) {
        this.title = title;
        return this;
    }

    public Feed changeDescription(String description) {
        this.description = description;
        return this;
    }

    public Feed changePrice(Integer price) {
        this.price = price;
        return this;
    }

    public void decreaseCount() {
    	this.count--;
	}

	public void increaseCount() {
    	this.count++;
	}

	public boolean isWriter(String email) {
    	return user.getEmail().equals(email);
	}

	public int getCurrentCount() {
    	if(!isUsedItem)
    		throw new RoomNotFoundException();
    	return group.getCurrentCount();
	}

}
