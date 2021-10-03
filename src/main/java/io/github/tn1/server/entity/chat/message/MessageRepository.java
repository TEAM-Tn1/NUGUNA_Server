package io.github.tn1.server.entity.chat.message;

import io.github.tn1.server.entity.chat.room.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
	Page<Message> findByRoom(Room room, Pageable pageable);
}
