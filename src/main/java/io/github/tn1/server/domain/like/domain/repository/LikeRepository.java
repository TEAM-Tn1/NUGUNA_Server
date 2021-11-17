package io.github.tn1.server.domain.like.domain.repository;

import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.like.domain.Like;
import io.github.tn1.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndFeed(User user, Feed feed);
}
