package io.github.tn1.server.global.security.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RequestLogger extends OncePerRequestFilter {

    private final LogWriter logWriter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getHeader("Content-Type") != null && request.getHeader("Content-Type").contains("multipart/form-data")){
            filterChain.doFilter(request, response);
        }else {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            filterChain.doFilter(requestWrapper, response);
            logRequest(requestWrapper, response);
        }
    }

    private void logRequest(RequestWrapper request, HttpServletResponse response) throws IOException {
        String requestTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        String requestIp = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .orElse("127.0.0.1");
        String method = request.getMethod();
        String url = request.getRequestURI();
        String params = request.getParamsString();
        int statusCode = response.getStatus();
        String body = request.getBody();

        String log = String.format("%s :: %s [%s] (%s%s %d) %s",
                requestTime, requestIp, method, url, params, statusCode, body);
        logWriter.writeLog(log);
        System.out.println(log);
    }

}
