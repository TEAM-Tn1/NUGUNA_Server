package io.github.tn1.server.entity.feed.medium;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedMediumRepository extends JpaRepository<FeedMedium, Long> {
}
