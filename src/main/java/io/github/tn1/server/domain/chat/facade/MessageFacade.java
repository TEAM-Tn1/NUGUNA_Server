package io.github.tn1.server.domain.chat.facade;

import io.github.tn1.server.domain.chat.domain.Member;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.repository.MemberRepository;
import io.github.tn1.server.domain.chat.domain.repository.MessageRepository;
import io.github.tn1.server.domain.chat.domain.types.MessageType;
import io.github.tn1.server.domain.chat.exception.NotYourRoomException;
import io.github.tn1.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageFacade {

	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;

	public Message saveMessage(User user, MessageType type, Room room, String content) {
		Member member = memberRepository.findByUserAndRoom(user, room)
				.orElseThrow(NotYourRoomException::new);
		return messageRepository.save(
				Message.builder()
				.member(member)
				.type(type)
				.room(room)
				.content(content)
				.build()
		);
	}

	public void setNullByMember(Member member) {
		messageRepository
				.setNullByMember(member.getId());
	}

}
