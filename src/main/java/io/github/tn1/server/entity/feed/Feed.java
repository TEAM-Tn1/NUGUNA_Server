package io.github.tn1.server.entity.feed;

import io.github.tn1.server.entity.BaseTimeEntity;
import io.github.tn1.server.entity.feed.group.Group;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.tag.Tag;
import io.github.tn1.server.entity.like.Like;
import io.github.tn1.server.entity.report.feed_report.FeedReport;
import io.github.tn1.server.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private Set<FeedMedium> media = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed", cascade = CascadeType.REMOVE)
    private Set<FeedReport> feedReports = new HashSet<>();

    @Builder
    public Feed(String title, String description, Integer price,
                boolean isUsedItem, User user) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.isUsedItem = isUsedItem;
        this.user = user;
    }

    public Feed setTitle(String title) {
        this.title = title;
        return this;
    }

    public Feed setDescription(String description) {
        this.description = description;
        return this;
    }

    public Feed setPrice(Integer price) {
        this.price = price;
        return this;
    }

}
