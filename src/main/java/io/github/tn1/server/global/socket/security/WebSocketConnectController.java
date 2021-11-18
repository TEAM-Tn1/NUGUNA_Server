package io.github.tn1.server.global.socket.security;

import com.corundumstudio.socketio.SocketIOClient;
import io.github.tn1.server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketConnectController {

	private final JwtTokenProvider jwtTokenProvider;

	public void onConnect(SocketIOClient client) {
		String token = client.getHandshakeData().getSingleUrlParam("Authorization");
		Authentication authentication = jwtTokenProvider.authentication(token);
		client.set(AuthenticationProperty.USER_KEY, authentication.getName());
	}

}
