package io.github.tn1.server.entity.feed.tag;

import io.github.tn1.server.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByFeedOrderById(Feed feed);
}
