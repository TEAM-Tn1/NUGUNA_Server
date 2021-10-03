package io.github.tn1.server.entity.feed.group;

import java.util.Optional;

import io.github.tn1.server.entity.feed.Feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findByFeed(Feed feed);
}
