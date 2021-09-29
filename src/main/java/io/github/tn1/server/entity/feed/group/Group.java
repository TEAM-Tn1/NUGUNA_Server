package io.github.tn1.server.entity.feed.group;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.github.tn1.server.entity.feed.Feed;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_group_buying_feed")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer headCount;

    private Integer currentCount;

    private LocalDate recruitmentDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Builder
    public Group(Integer headCount, LocalDate recruitmentDate,
                 Feed feed) {
        this.headCount = headCount;
        this.recruitmentDate = recruitmentDate;
        this.feed = feed;
    }

    public void increaseCurrentCount() {
    	this.currentCount++;
	}

	public void decreaseCurrentCount() {
    	this.currentCount--;
	}

}
