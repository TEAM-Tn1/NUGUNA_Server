package io.github.tn1.server.domain.chat.domain.repository;

import java.util.Optional;

import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
	Page<Message> findByRoom(Room room, Pageable pageable);
	Optional<Message> findTopByRoom(Room room);

	@Modifying
	@Query("update tbl_message m set m.member = null where m.member.id = :memberId")
	void setNullByMember(Long memberId);
}
