package io.github.tn1.server.domain.feed.domain.repository;

import java.util.List;

import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.user.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByUser(User user);
    Page<Feed> findByUserAndIsUsedItem(User user, boolean isUsedItem, Pageable pageable);
    Page<Feed> findByIsUsedItem(boolean isUsedItem, Pageable pageable);
    Page<Feed> findByTitleContainsAndIsUsedItem(String title, boolean isUsedItem, Pageable pageable);
}
