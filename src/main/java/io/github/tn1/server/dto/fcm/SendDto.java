package io.github.tn1.server.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SendDto {
    private final String token;
    private final String title;
    private final String message;
    private final String key;
    private final String data;
}
