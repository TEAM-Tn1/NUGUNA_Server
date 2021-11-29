package io.github.tn1.server.domain.feed.domain.repository;

import java.util.List;

import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.domain.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByFeedOrderById(Feed feed);

    @Modifying
    @Query("delete from tbl_tag t where t.feed.id = :feedId")
    void deleteByFeedId(Long feedId);
}
