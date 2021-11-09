package io.github.tn1.server.facade.user;

import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
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

    public User getCurrentUser() {
    	return userRepository
				.findById(getCurrentEmail())
				.orElseThrow(CredentialsNotFoundException::new);
	}

}
