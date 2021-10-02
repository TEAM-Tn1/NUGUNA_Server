package io.github.tn1.server.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    FILE_UPLOAD_FAIL(400, "File upload Fail"),
    FILE_EMPTY(400, "File is empty."),
	ITS_YOUR_FEED(400, "Its your feed."),
    INVALID_TOKEN(401, "Invalid token."),
    EXPIRED_ACCESS_TOKEN(401, "Expired access token."),
    EXPIRED_REFRESH_TOKEN(401, "Expired refresh token."),
    CREDENTIALS_NOT_FOUND(401, "Credentials not found."),
    NOT_YOUR_FEED(403, "Not your feed."),
    BLACKED(403, "User was blacked."),
    USER_NOT_FOUND(404, "User not found."),
    FEED_NOT_FOUND(404, "Feed not found."),
	MEDIUM_NOT_FOUND(404, "Medium not found."),
	ROOM_NOT_FOUND(404, "Room not found."),
	LIKE_NOT_FOUND(404, "Like not found."),
	ALREADY_LIKED_FEED(409, "Already liked feed."),
	ALREADY_JOIN_ROOM(409, "Already join room."),
    TOO_MANY_TAGS(413, "Too many tags."),
    TOO_MANY_FILES(413, "Too many tags."),

	OTHER_SERVER_BAD_REQUEST(400, "Bad Request."),
	OTHER_SERVER_UNAUTHORIZED(401, "Unauthorized token."),
	OTHER_SERVER_EXPIRED_TOKEN(401, "Expired token."),
	OTHER_SERVER_FORBIDDEN(403, "Forbidden Consumer.");

    private final int status;
    private final String message;

}
