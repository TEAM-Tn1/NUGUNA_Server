package io.github.tn1.server.domain.chat.domain.repository;

import java.util.Optional;

import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.types.RoomType;
import io.github.tn1.server.domain.feed.domain.Feed;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
	Optional<Room> findByFeed(Feed feed);

	@Query("select r from tbl_room r join tbl_room_member m on m.user.email = :email where r.id = m.room.id and r.type = :type")
	Optional<Room> findByEmailAndType(String email, RoomType type);

	@Query("select case when count(r.id) > 0 then true else false end from tbl_room r join tbl_room_member m on m.user.email = :email where r.feed = :feed and r.id = m.room.id")
	boolean existsByFeedAndEmail(Feed feed, String email);
}
