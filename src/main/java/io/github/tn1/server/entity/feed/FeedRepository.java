package io.github.tn1.server.entity.feed;

import io.github.tn1.server.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByUser(User user);
    Page<Feed> findByIsUsedItem(boolean isUsedItem, Pageable pageable);
}
