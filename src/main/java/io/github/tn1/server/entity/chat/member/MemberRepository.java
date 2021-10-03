package io.github.tn1.server.entity.chat.member;

import java.util.Optional;

import io.github.tn1.server.entity.chat.room.Room;
import io.github.tn1.server.entity.user.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
	Optional<Member> findByUserAndRoom(User user, Room room);
}
