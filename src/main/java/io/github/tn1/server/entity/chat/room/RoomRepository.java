package io.github.tn1.server.entity.chat.room;

import java.util.Optional;

import io.github.tn1.server.entity.feed.Feed;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
	Optional<Room> findByFeed(Feed feed);
}
