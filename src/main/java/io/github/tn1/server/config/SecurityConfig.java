package io.github.tn1.server.config;

import io.github.tn1.server.error.ExceptionHandlerFilter;
import io.github.tn1.server.security.jwt.JwtTokenProvider;
import io.github.tn1.server.security.logging.RequestLogger;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final RequestLogger requestLogger;

    private static final String[] roles = {"USER", "ADMIN"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/oauth").permitAll()
                .antMatchers(HttpMethod.POST, "/users/auth").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/auth").permitAll()
                .antMatchers(HttpMethod.PATCH, "/users/information").hasAnyRole(roles)
                .antMatchers(HttpMethod.GET, "/users/{email}").permitAll()

                .antMatchers(HttpMethod.GET, "/feed/users/{email}").permitAll()
				.antMatchers(HttpMethod.GET, "/feed/{feed_id}").permitAll()
                .antMatchers(HttpMethod.GET, "/feed/carrot").permitAll()
                .antMatchers(HttpMethod.POST, "/feed/carrot").hasAnyRole(roles)
                .antMatchers(HttpMethod.PATCH, "/feed/carrot").hasAnyRole(roles)
                .antMatchers(HttpMethod.DELETE, "/feed/{feed_id}").hasAnyRole(roles)
                .anyRequest().authenticated()
                .and().apply(new FilterConfig(jwtTokenProvider, exceptionHandlerFilter, requestLogger));

    }
}
