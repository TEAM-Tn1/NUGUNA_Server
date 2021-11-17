package io.github.tn1.server.global.config;

import io.github.tn1.server.global.error.ExceptionHandlerFilter;
import io.github.tn1.server.global.security.jwt.JwtTokenFilter;
import io.github.tn1.server.global.security.jwt.JwtTokenProvider;
import io.github.tn1.server.global.security.logging.RequestLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final RequestLogger requestLogger;

    @Override
    public void configure(HttpSecurity builder) {
        JwtTokenFilter filter = new JwtTokenFilter(jwtTokenProvider);
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(exceptionHandlerFilter, JwtTokenFilter.class);
        builder.addFilterBefore(requestLogger, ExceptionHandlerFilter.class);
    }

}
