package io.github.tn1.server.entity.like;

import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndFeed(User user, Feed feed);
}
