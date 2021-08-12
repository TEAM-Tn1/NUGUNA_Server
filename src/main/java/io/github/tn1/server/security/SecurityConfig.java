package io.github.tn1.server.security;

import io.github.tn1.server.security.jwt.FilterConfigure;
import io.github.tn1.server.security.jwt.JwtTokenProvider;
import io.github.tn1.server.security.logging.RequestLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RequestLogger requestLogger;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers("/users/oauth").permitAll()
                .anyRequest().authenticated()
                .and().apply(new FilterConfigure(jwtTokenProvider))
                .and().addFilterAfter(requestLogger, FilterSecurityInterceptor.class);
    }
}
