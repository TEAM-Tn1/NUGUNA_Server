package io.github.tn1.server.domain.chat.domain.repository;

import java.util.Optional;

import io.github.tn1.server.domain.chat.domain.Member;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.user.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
	Optional<Member> findByUserAndRoom(User user, Room room);
}
