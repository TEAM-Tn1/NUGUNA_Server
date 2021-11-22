package io.github.tn1.server.global.socket.exception;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import io.github.tn1.server.global.error.ErrorResponse;
import io.github.tn1.server.global.error.exception.ServerException;
import io.github.tn1.server.global.socket.SocketProperty;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Component;

@Component
public class SocketExceptionListener implements ExceptionListener {

	@Override
	public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
		runExceptionHandling(e, client);
	}

	@Override
	public void onDisconnectException(Exception e, SocketIOClient client) {
		runExceptionHandling(e, client);
	}

	@Override
	public void onConnectException(Exception e, SocketIOClient client) {
		runExceptionHandling(e, client);
		client.disconnect();
	}

	@Override
	public void onPingException(Exception e, SocketIOClient client) {
		runExceptionHandling(e, client);
	}

	@Override
	public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		return false;
	}

	private void runExceptionHandling(Exception e, SocketIOClient client) {
		final ErrorResponse message;

		if (e.getCause() instanceof ServerException) {
			ServerException serverException = (ServerException)e.getCause();
			message = ErrorResponse.builder()
					.message(serverException.getErrorCode().getMessage())
					.status(serverException.getErrorCode().getStatus())
					.build();
		} else {
			e.printStackTrace();
			message = ErrorResponse.builder()
					.status(500)
					.message("Internal Server Error")
					.build();
		}

		client.sendEvent(SocketProperty.ERROR_KEY, message);

	}

}
