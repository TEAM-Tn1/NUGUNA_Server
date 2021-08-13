package io.github.tn1.server.entity.feed.medium;

import io.github.tn1.server.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedMediumRepository extends JpaRepository<FeedMedium, Long> {
    FeedMedium findTopByFeedOrderById(Feed feed);

}
