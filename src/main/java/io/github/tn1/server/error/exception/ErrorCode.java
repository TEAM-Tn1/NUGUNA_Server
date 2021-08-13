package io.github.tn1.server.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    USER_NOT_FOUND(404, "User not found."),
    INVALID_TOKEN(401, "Invalid token."),
    EXPIRED_REFRESH_TOKEN(401, "Expired refresh token."),
    BLACKED(403, "User was blacked.");

    private final int status;
    private final String message;

}
