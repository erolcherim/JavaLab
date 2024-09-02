package com.unibuc.laborator.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * We have to add this if we want to throw exceptions in filters otherwise the default exceptionHandler does not work
 */

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response);
        } catch (RuntimeException e) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write("Token is invalid");
    }
}
