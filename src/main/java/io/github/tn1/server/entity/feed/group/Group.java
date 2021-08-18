package io.github.tn1.server.entity.feed.group;

import io.github.tn1.server.entity.feed.Feed;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
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

}
