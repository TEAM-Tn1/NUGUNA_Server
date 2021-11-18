package io.github.tn1.server.global.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(1)
public class SocketRunner implements CommandLineRunner {

	private final SocketIOServer socketIOServer;

	@Override
	public void run(String... args) throws Exception {
		socketIOServer.start();
	}

}
