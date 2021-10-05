package io.github.tn1.server.dto.fcm;

import io.github.tn1.server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SendDto {
	private final User user;
    private final String title;
    private final String message;
    private final String key;
    private final String data;
}
