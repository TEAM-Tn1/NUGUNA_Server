package io.github.tn1.server.security.facade;

import io.github.tn1.server.exception.CredentialsNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFacade {

    public static String getEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal() == null)
            throw new CredentialsNotFoundException();

        return authentication.getName();
    }

}
