package io.github.tn1.server.domain.feed.domain.repository;

import java.util.Optional;

import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.domain.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findByFeed(Feed feed);
}
