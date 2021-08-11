package io.github.tn1.server.security.jwt;

import io.github.tn1.server.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if(token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.authentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidTokenException e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

}
