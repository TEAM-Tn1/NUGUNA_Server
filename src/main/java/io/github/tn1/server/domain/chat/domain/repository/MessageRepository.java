package io.github.tn1.server.domain.chat.domain.repository;

import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
	Page<Message> findByRoom(Room room, Pageable pageable);
}
