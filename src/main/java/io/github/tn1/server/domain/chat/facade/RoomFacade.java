package io.github.tn1.server.domain.chat.facade;

import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.repository.RoomRepository;
import io.github.tn1.server.domain.chat.exception.RoomNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomFacade {

	private final RoomRepository roomRepository;

	public Room getRoomById(String roomId) {
		return roomRepository.findById(roomId)
				.orElseThrow(RoomNotFoundException::new);
	}

}
