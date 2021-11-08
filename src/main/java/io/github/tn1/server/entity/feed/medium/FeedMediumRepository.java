package io.github.tn1.server.entity.feed.medium;

import java.util.List;
import java.util.Optional;

import io.github.tn1.server.entity.feed.Feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedMediumRepository extends JpaRepository<FeedMedium, Long> {
    FeedMedium findTopByFeedOrderById(Feed feed);
    int countByFeed(Feed feed);
    Optional<FeedMedium> findByFileName(String fileName);
    void removeByFeed(Feed feed);
    List<FeedMedium> findByFeedOrderById(Feed feed);
}
