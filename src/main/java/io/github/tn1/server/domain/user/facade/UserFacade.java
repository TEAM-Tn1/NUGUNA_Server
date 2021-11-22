package io.github.tn1.server.domain.user.facade;

import com.corundumstudio.socketio.SocketIOClient;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.domain.repository.UserRepository;
import io.github.tn1.server.domain.user.exception.CredentialsNotFoundException;
import io.github.tn1.server.domain.user.exception.UserNotFoundException;
import io.github.tn1.server.global.socket.security.AuthenticationProperty;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

	private final UserRepository userRepository;

    public String getCurrentEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal() == null)
            throw new CredentialsNotFoundException();

        return authentication.getName();
    }

    public String getCurrentEmail(SocketIOClient client) {
    	return client.get(AuthenticationProperty.USER_KEY);
	}

    public User getCurrentUser() {
    	return userRepository
				.findById(getCurrentEmail())
				.orElseThrow(CredentialsNotFoundException::new);
	}

	public User getCurrentUser(SocketIOClient client) {
    	return userRepository
				.findById(getCurrentEmail(client))
				.orElseThrow(CredentialsNotFoundException::new);
	}

	public User getUserByEmail(String email) {
    	return userRepository
				.findById(email)
				.orElseThrow(UserNotFoundException::new);
	}

}
