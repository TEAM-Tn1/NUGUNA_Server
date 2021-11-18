package io.github.tn1.server.global.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.global.socket.WebSocketAddMappingSupporter;
import io.github.tn1.server.global.socket.exception.SocketExceptionListener;
import io.github.tn1.server.global.socket.security.WebSocketConnectController;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketConfig {

	private final WebSocketAddMappingSupporter mappingSupporter;
	private final WebSocketConnectController connectController;
	private final SocketExceptionListener exceptionListener;

	@Value("${socket.port}")
	private Integer port;

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setPort(port);
		config.setOrigin("*");
		config.setExceptionListener(exceptionListener);
		SocketIOServer server = new SocketIOServer(config);
		mappingSupporter.addListeners(server);
		server.addConnectListener(connectController::onConnect);
		return server;
	}

}
