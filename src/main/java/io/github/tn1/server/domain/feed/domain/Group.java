package io.github.tn1.server.domain.feed.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.github.tn1.server.domain.feed.domain.Feed;
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

    private int headCount;

    @Column(columnDefinition = "INT default 1")
    private int currentCount;

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

	public void changeHeadCount(int headCount) {
    	this.headCount = headCount;
	}

	public void changeDate(LocalDate recruitmentDate) {
    	this.recruitmentDate = recruitmentDate;
	}

}
