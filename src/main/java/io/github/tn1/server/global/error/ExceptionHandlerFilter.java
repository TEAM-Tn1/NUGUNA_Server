package io.github.tn1.server.global.error;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (ServerException e) {
            ErrorCode errorCode = e.getErrorCode();
            ErrorResponse errorResponse =
                    new ErrorResponse(errorCode.getStatus(), errorCode.getMessage());
            response.setStatus(errorCode.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(errorResponse.toString());
        }
    }

}
